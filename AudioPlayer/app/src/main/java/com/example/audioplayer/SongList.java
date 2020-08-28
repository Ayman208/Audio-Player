package com.example.audioplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class SongList extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 99 ;

    ArrayList<Songs> songsArrayList ;
    ListView songslistview ;
    SongsAdapter songsAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        songslistview = findViewById(R.id.list_view_songs);
        songsArrayList = new ArrayList<>();

        songsAdapter = new SongsAdapter(this , songsArrayList);
        songslistview.setAdapter(songsAdapter);

        // permission to read songs from phone
        if (ActivityCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            return;
        } else {
            //you have permission to read from extrnal storage
            getSongs();
        }
        songslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Songs song = songsArrayList.get(position);
                Intent openMainActivity = new Intent(SongList. this , SongList.class);
                openMainActivity.putExtra("song", song);
                startActivity(openMainActivity);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getSongs();
            }
        }
    }





    private void getSongs(){
        // read songs from phone


        ContentResolver contentResolver = getContentResolver();
        Uri songuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ;

        Cursor songscursor = contentResolver.query(songuri , null , null , null , null);
        if (songscursor!=null && songscursor.moveToFirst()){

            int indextitle = songscursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int indexartist = songscursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int indexpath = songscursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String title = songscursor.getString(indextitle);
                String artist = songscursor.getString(indexartist);
                String path = songscursor.getString(indexpath);
                songsArrayList.add(new Songs(title , artist , path));


            } while (songscursor.moveToNext());
        }
        songsAdapter.notifyDataSetChanged();
    }

}