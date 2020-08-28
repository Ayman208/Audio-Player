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



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //views declartion
    TextView left_time, full_time , songname , singername;
    SeekBar seek_bar_time ;
    Button btn_play , btn_list ;
    MediaPlayer mediaPlayer;
    AudioManager audiomanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Songs song =  (Songs) getIntent().getSerializableExtra("song");


        left_time = findViewById(R.id.left_time);
        full_time = findViewById(R.id.full_time);

        songname = findViewById(R.id.song_name);
        singername = findViewById(R.id.singer_name);
        songname.setText(song.getTittle());
        singername.setText(song.getArtist());


        mediaPlayer = new  MediaPlayer();
        try {
            mediaPlayer.setDataSource(song.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        


        btn_list = findViewById(R.id.songs_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SongsList = new Intent(MainActivity.this,SongList.class);
                startActivity(SongsList);

            }
        });


        btn_play = findViewById(R.id.btn_play);
        btn_play.setOnClickListener(this);




// adjust seekbar of time
        String duration = millisecondsToString(mediaPlayer.getDuration());
        full_time.setText(duration);
        seek_bar_time = findViewById(R.id.seek_bar_time);
        seek_bar_time.setMax(mediaPlayer.getDuration());
        seek_bar_time.setOnSeekBarChangeListener(new SeekBar. OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress , boolean isFromUser){
                if(isFromUser){
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){


            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        });


        new Thread(new Runnable(){
            @Override
            public void run (){
                while(mediaPlayer != null){
                    if(mediaPlayer.isPlaying()){
                        try{
                            final double current = mediaPlayer.getCurrentPosition();
                            final String elapsedTime = millisecondsToString((int) current );

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run(){
                                    left_time.setText(elapsedTime);
                                    seek_bar_time.setProgress((int) current);
                                }
                            });

                            Thread.sleep(1000);
                        }catch(InterruptedException e){}
                    }

                }
            }
        }).start();


    } // end main
    public String millisecondsToString(int time){
        String elapsedTime ="";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes+":";
        if(seconds<10) {
            elapsedTime +="0";
        }
        elapsedTime += seconds;
        return elapsedTime;
    }



    // code for play button
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_play) {
            if (mediaPlayer.isPlaying()) {
                //playing
                mediaPlayer.pause();
                btn_play.setBackgroundResource(R.drawable.play);
            } else {
                //pause
                mediaPlayer.start();
                btn_play.setBackgroundResource(R.drawable.pause);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            finish();
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}