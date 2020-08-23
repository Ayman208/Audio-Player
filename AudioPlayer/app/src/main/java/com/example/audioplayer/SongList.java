package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SongList extends AppCompatActivity {
   ArrayList<Songs> songsArrayList ;
   ListView songslistview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        songslistview = findViewById(R.id.list_view_songs);
        songsArrayList = new ArrayList<>();
        for (int i = 1 ; i<=10 ; i++)
            songsArrayList.add(new Songs("Song"+i ,"Artist"+i ,"Path"+i));


        SongsAdapter songsAdapter = new SongsAdapter(this , songsArrayList);
        songslistview.setAdapter(songsAdapter);
    }
}