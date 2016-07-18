package com.mfrockola.classes;

/**
 * Created by Angel C on 18/07/2016.
 */
public class CancionDB {
    private int _ID;
    private int number;
    private String name;
    private int times;
    private long date;
    private String artist;
    private String genre;

    public CancionDB(int _ID, int number, String name, int times, long date, String artist, String genre) {
        set_ID(_ID);
        setNumber(number);
        setName(name);
        setTimes(times);
        setDate(date);
        setArtist(artist);
        setGenre(genre);
    }

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

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
