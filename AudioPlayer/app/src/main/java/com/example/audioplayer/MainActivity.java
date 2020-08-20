package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;
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
    Button btn_play;
    MediaPlayer AudioPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        left_time = findViewById(R.id.left_time);
        full_time = findViewById(R.id.full_time);
        seek_bar_time = findViewById(R.id.seek_bar_time);
        seek_bar_vol = findViewById(R.id.seek_bar_vol);
        btn_play = findViewById(R.id.btn_play);
        AudioPlayer = MediaPlayer.create(MainActivity.this,R.raw.aaa);
        AudioPlayer.setLooping(true);
        AudioPlayer.seekTo(0);
        AudioPlayer.setVolume(0.5f, 0.5f);
        btn_play.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_play) {
            if (AudioPlayer.isPlaying()) {
                //playing
                AudioPlayer.pause();
                btn_play.setBackgroundResource(R.drawable.play);
            } else {
                  //pause
                AudioPlayer.start();
                btn_play.setBackgroundResource(R.drawable.pause);
            }
        }
    }
}