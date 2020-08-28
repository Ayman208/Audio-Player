package com.example.audioplayer;

import java.io.Serializable;

public class Songs implements Serializable {

    private String tittle ;
    private String artist ;
    private String path;

    public Songs(String tittle, String artist, String path) {
        this.tittle = tittle;
        this.artist = artist;
        this.path = path;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
