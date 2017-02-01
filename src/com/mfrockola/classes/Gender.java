package com.mfrockola.classes;

/**
 * This class is the definition of a musical genre. Contains its name and the list of songs in a Song arrangement
 */
public class Gender {

    // private variable to store the gender name.
    private String name;

    // private variable to store the list of music that contains the gender.
    private Song[] songs;

    // in the constructor we establish the private variables
    public Gender(String name, Song[] songs) {
        setName(name);
        setSongs(songs);
    }

    // Public methods to obtain the values ​​of the private variables of the Gender class
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
