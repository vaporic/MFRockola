package com.mfrockola.classes;

import javax.swing.Timer;

/**
 * This class stores the songs that are locked to play and the time in which the counter that unlocks them will be reset
 */

class BlockedSongs
{
    // Arrangement with blocked songs. We must initialize it
    private int blockedSongs[] = {-1};
	
	BlockedSongs(int minutes) {
        // Set the delay in minutes
        int delay = 1000*60*minutes;

        // This Timer will restart blocked songs
        Timer timer = new Timer(delay, e -> blockedSongs = new int[]{-1});
		
		timer.start();
	}

    // this method block a song.
    void blockSong(int numberSong) {

        int array[] = new int[blockedSongs.length+1];

        System.arraycopy(blockedSongs, 0, array, 0, blockedSongs.length);
        array[array.length-1] = numberSong;
        blockedSongs = array;
    }

    // this method check the blocked songs.
    boolean checkBlockedSongs(int numberSong) {
		boolean blocked = true;

        for (int blockedSong : blockedSongs) {
            if (numberSong == blockedSong)
                blocked = false;
        }
		return blocked;
	}
}
