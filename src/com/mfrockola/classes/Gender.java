package com.mfrockola.classes;

/**
 * Created by Angel C on 03/05/2016.
 */
public class Gender {

    private String name;
    private Song[] songs;

    public Gender(String name, Song[] songs) {
        setName(name);
        setSongs(songs);
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public Song[] getSongs() {
        return songs;
    }

    public void setSongs(Song[] songs) {
        this.songs = songs;
    }
}
