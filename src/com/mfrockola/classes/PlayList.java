package com.mfrockola.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class PlayList {
    private ArrayList<Song> mPlayList;

    PlayList(){
        mPlayList = new ArrayList<>();
    }

    void addSong (Song song) {
        mPlayList.add(song);
    }

    String songToPlay() {
        if (mPlayList.size()==0) {
            return null;
        } else {
            return mPlayList.get(0).getSongName();
        }
    }

    int getSongNumber() {
        return mPlayList.get(0).getSongNumber();
    }

    String getSongGender() {
        return mPlayList.get(0).getSongGenre();
    }

    String getSinger() {
        return mPlayList.get(0).getSinger();
    }

    void removeSong() {
        if (mPlayList.size()>0) {
            mPlayList.remove(0);
        }
    }

    public JSONObject getSongJSONObject(Song song) {
        JSONObject songJsonObject = new JSONObject();

        try {
            songJsonObject.put("songNumber",song.getSongNumber());
            songJsonObject.put("songGenre",song.getSongGenre());
            songJsonObject.put("songSinger",song.getSinger());
            songJsonObject.put("songName",song.getSongName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songJsonObject;
    }

    Object[] getPlayList(){
        return mPlayList.toArray();
    }
}