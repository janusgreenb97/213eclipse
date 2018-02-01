package songlib.model;

public class Song {
	
	private String name, artist, album;
	int year;

	
	//constuctors for song objs
	public Song(	String name, String artist, String album, int year){
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	/*
	public Song(){
		this("Testing", "Testing", "unknown", 0);
	}
	*/	
	public Song(	String name, String artist, String album){
		this(name, artist, album, -1);
	}
	public Song(	String name, String artist, int year){
		this(name, artist, "unknown", year);
	}	
	public Song(	String name, String artist){
		this(name, artist, "unknown", -1);
	}

	//getters
	public String getSongName() {
		return this.name;
	}
	public String getArtist() {
		return this.artist;
	}
	public String getAlbum() {
		return this.album;
	}
	public int getYear() {
		return this.year;
	}
	
	//setters
	public void setSoneName(String newSongName) {
		this.name = newSongName;
	}
	public void setArtist(String newArtist) {
		this.artist = newArtist;
	}
	public void setAlbum(String newAlbum) {
		this.album = newAlbum;
	}
	public void setYear(int newYear) {
		this.year = newYear;
	}
	
	
	//utility methods
	public String toString() {
		return "song: " + name + " artist: " + artist;
	}
	public String toString_detial() {
		return toString() + " album: " + album + " year: " + year;
	}
	
}
