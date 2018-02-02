package songlib.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {

	private StringProperty name, artist, album;
	private IntegerProperty year;

	// constructors for song objects
	public Song(String name, String artist, String album, int year) {
		this.name = new SimpleStringProperty(name);
		this.artist = new SimpleStringProperty(artist);
		this.album = new SimpleStringProperty(album);
		this.year = new SimpleIntegerProperty(year);
	}

	/*
	 * public Song(){ this("Testing", "Testing", "unknown", 0); }
	 */
	public Song(String name, String artist, String album) {
		this(name, artist, album, -1);
	}

	public Song(String name, String artist, int year) {
		this(name, artist, "unknown", year);
	}

	public Song(String name, String artist) {
		this(name, artist, "unknown", -1);
	}

	//getters for properties
	public StringProperty nameProperty() {
        return name;
    }
	public StringProperty artistProperty() {
        return artist;
    }
	public StringProperty albumProperty() {
        return album;
    }
	public IntegerProperty yearProperty() {
        return year;
    }

	// getters for strings
	public String getSongName() {
		return name.get();
	}

	public String getArtist() {
		return artist.get();
	}

	public String getAlbum() {
		return album.get();
	}

	public int getYear() {
		return year.get();
	}

	// setters
	public void setSoneName(String newSongName) {
		this.name.set(newSongName);
	}

	public void setArtist(String newArtist) {
		this.artist.set(newArtist);
	}

	public void setAlbum(String newAlbum) {
		this.album.set(newAlbum);
	}

	public void setYear(int newYear) {
		this.year.set(newYear);
	}

	// utility methods
	public String toString() {
		return "song: " + name + " artist: " + artist;
	}

	public String toString_detial() {
		return toString() + " album: " + album + " year: " + year;
	}

}
