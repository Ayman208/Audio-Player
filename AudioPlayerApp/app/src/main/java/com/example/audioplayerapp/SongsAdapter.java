package com.example.audioplayerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SongsAdapter  extends ArrayAdapter<Songs> {


    public SongsAdapter(@NonNull Context context, @NonNull List<Songs> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item , null);
        TextView songtitle = convertView.findViewById(R.id.song_title);
        TextView songartist = convertView.findViewById(R.id.song_artist);

        Songs song = getItem(position);
        songtitle.setText(song.getTittle());
        songartist.setText(song.getArtist());
        return convertView;
    }
}
