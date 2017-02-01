package com.mfrockola.classes;

/**
 * This class contains the basic data of a song that will be stored in the database of the most listened
 */
public class SongDataBase {

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
    public SongDataBase(int _ID, int number, String name, int times, long date, String singer, String genre) {
        set_ID(_ID);
        setNumber(number);
        setName(name);
        setTimes(times);
        setDate(date);
        setSinger(singer);
        setGenre(genre);
    }

    // Public methods for calling private variables from external classes

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
