package com.mfrockola.classes;

/**
 * Created by Angel C on 03/05/2016.
 */
public class Gender {

    private String name;
    private Cancion[] songs;

    public Gender(String name, Cancion[] songs) {
        setName(name);
        setSongs(songs);
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public Cancion[] getSongs() {
        return songs;
    }

    public void setSongs(Cancion[] songs) {
        this.songs = songs;
    }
}
