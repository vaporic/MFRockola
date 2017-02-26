package com.mfrockola.classes;

class PlayList {
    private Song[] playList;

    PlayList() {
        playList = new Song[1];
    }

    void addSong(Song song) {
        Song[] provisional = new Song[playList.length + 1];

        if (playList [0] == null) {
            playList[0] = song;
        } else {
            System.arraycopy(playList, 0, provisional, 0, playList.length);
            playList = provisional;
            playList[playList.length-1] = song;
        }
    }

    String songToPlay() {
        if (playList[0]==null)
            return null;
        else
            return playList[0].getSongName();
    }

    int getSongNumber() {
        return playList[0].getSongNumber();
    }

    String getSongGender() {
        return playList[0].getSongGenre();
    }

    String getSinger() {
        return playList[0].getSinger();
    }

    void removeSong() {
        if (playList.length == 1) {
            playList[0]= null;
        } else {
            Song[] provisional = new Song[playList.length - 1];
            System.arraycopy(playList, 1, provisional, 0, playList.length - 1);
            playList = provisional;
        }
    }

    Song[] getPlayList() {
        return playList;
    }
}
