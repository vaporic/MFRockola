package com.mfrockola.classes;

/**
 * This class is the definition of a musical genre. Contains its name and the list of songs in a Song arrangement
 */
class Gender {

    // private variable to store the gender name.
    private String name;

    // private variable to store the list of music that contains the gender.
    private Song[] songs;

    // in the constructor we establish the private variables
    Gender(String name, Song[] songs) {
        setName(name);
        setSongs(songs);
    }

    // Public methods to obtain the values ​​of the private variables of the Gender class
    String getName() {
        return name;
    }

    private void setName(String nombre) {
        this.name = nombre;
    }

    Song[] getSongs() {
        return songs;
    }

    private void setSongs(Song[] songs) {
        this.songs = songs;
    }
}
