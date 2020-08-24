package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //views declartion
    TextView left_time, full_time;
    SeekBar seek_bar_time, seek_bar_vol;
    Button btn_play , btn_list ;
    MediaPlayer mediaPlayer;
     AudioManager audiomanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        left_time = findViewById(R.id.left_time);
        full_time = findViewById(R.id.full_time);
        seek_bar_time = findViewById(R.id.seek_bar_time);
        btn_play = findViewById(R.id.btn_play);
        btn_list = findViewById(R.id.songs_list);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.aaa);
        btn_play.setOnClickListener(this);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SongsList = new Intent(MainActivity.this,SongList.class);
                startActivity(SongsList);

                String duration = millisecondsToString(mediaPlayer.getDuration());
                full_time.setText(duration);
            }
        });

        


//  AdjustseekBar of volume
        audiomanager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxvol = audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentvol = audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seek_bar_vol = findViewById(R.id.seek_bar_vol);
        seek_bar_vol.setMax(maxvol);
        seek_bar_vol.setProgress(currentvol);
        seek_bar_vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC , progress , 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





// adjust seekbar of time
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