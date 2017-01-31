package com.mfrockola.classes;

// This class contains the basic data of a song, such as its number, name, singer and genre.

public class Song {
	private int songNumber;
	private String songGenre;
	private String singer;
	private String songName;

	// Constructor to start variables
	public Song(int songNumber, String songGenre, String singer, String songName) {
		setSongNumber(songNumber);
		setSongGenre(songGenre);
		setSinger(singer);
		setSongName(songName);
	}

	// Public methods to obtain the values ​​of the variables of the Song object
	
	public String getSongName() {
		return songName;
	}
	
	public int getSongNumber() {
		return songNumber;
	}
	
	public void setSongNumber(int songNumber) {
		this.songNumber = songNumber;
	}
	
	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getSongGenre() {
		return songGenre;
	}

	public void setSongGenre(String songGenre) {
		this.songGenre = songGenre;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	// Override the toString method in order to print the song name correctly on the interface
	@Override
	public String toString() {
		return String.format("%04d - %s - %s", getSongNumber(),getSinger(), getSongName());
	}
}
