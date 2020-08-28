package com.example.audioplayerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    //views declartion
    TextView left_time, full_time , songname , singername;
    SeekBar seek_bar_time ;
    Button btn_play  ;
    MediaPlayer mediaPlayer ;
    AudioManager audiomanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


       Songs song = (Songs) getIntent().getSerializableExtra("song");

        left_time = findViewById(R.id.left_time);
        full_time = findViewById(R.id.full_time);

        songname = findViewById(R.id.song_name);
        singername = findViewById(R.id.singer_name);


        mediaPlayer=MediaPlayer.create(this , R.raw.aaa);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);




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




}