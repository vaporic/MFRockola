package com.mfrockola.classes;

/**
 * This class contains the basic data of a song that will be stored in the database of the most listened
 */
class SongDataBase {

    // Private variables that we can call in the database

    // ID of song
    private int _ID;

    // Number of the song in the list
    private int number;

    // Name of dong
    private String name;

    // Times the song has been played
    private int times;

    // Date of last reproduction
    private long date;

    // Singer of song
    private String singer;

    // Genre of song
    private String genre;

    // We set values ​​of the private variables in the constructor when we create the SongDataBase object
    SongDataBase(int _ID, int number, String name, int times, long date, String singer, String genre) {
        set_ID(_ID);
        setNumber(number);
        setName(name);
        setTimes(times);
        setDate(date);
        setSinger(singer);
        setGenre(genre);
    }

    // Public methods for calling private variables from external classes

    private void set_ID(int _ID) {
        this._ID = _ID;
    }

    int getNumber() {
        return number;
    }

    private void setNumber(int number) {
        this.number = number;
    }

    String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    int getTimes() {
        return times;
    }

    private void setTimes(int times) {
        this.times = times;
    }

    long getDate() {
        return date;
    }

    private void setDate(long date) {
        this.date = date;
    }

    String getSinger() {
        return this.singer;
    }

    private void setSinger(String singer) {
        this.singer = singer;
    }

    String getGenre() {
        return this.genre;
    }

    private void setGenre(String genre) {
        this.genre = genre;
    }
}
