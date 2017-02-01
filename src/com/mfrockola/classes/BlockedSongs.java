package com.mfrockola.classes;

import javax.swing.Timer;

/**
 * This class stores the songs that are locked to play and the time in which the counter that unlocks them will be reset
 */

public class BlockedSongs
{
    // Arrangement with blocked songs. We must initialize it
    private int blockedSongs[] = {-1};
	
	public BlockedSongs(int minutes) {
        // Set the delay in minutes
        int delay = 1000*60*minutes;

        // This Timer will restart blocked songs
        Timer timer = new Timer(delay, e -> {
            int [] array = {-1};
            blockedSongs = array;
        });
		
		timer.start();
	}

    // this method block a song.
    public void blockSong(int numberSong) {

        int array[] = new int[blockedSongs.length+1];

        for(int i = 0; i < blockedSongs.length; ++i) {
            array[i]=blockedSongs[i];
        }
        array[array.length-1] = numberSong;
        blockedSongs = array;
    }

    // this method check the blocked songs.
	public boolean checkBlockedSongs(int numberSong) {
		boolean blocked = true;
		
		for(int i = 0; i < blockedSongs.length; ++i) {
			if(numberSong == blockedSongs[i])
				blocked = false;
		}
		
		return blocked;
	}
}
