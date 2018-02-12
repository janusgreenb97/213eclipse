package songlib.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song implements Comparable<Song> {

	private StringProperty name, artist, album;
	private StringProperty year;

	// constructors for song objects
	public Song(String name, String artist, String album, String year) {
		this.name = new SimpleStringProperty(name);
		this.artist = new SimpleStringProperty(artist);
		this.album = new SimpleStringProperty(album);
		this.year = new SimpleStringProperty(year);
	}

	public Song(String name, String artist) {
		this(name, artist, "unknown", "unknown");
	}

	// getters for properties
	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty artistProperty() {
		return artist;
	}

	public StringProperty albumProperty() {
		return album;
	}

	public StringProperty yearProperty() {
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

	public String getYear() {
		return year.get();
	}

	// setters
	public void setSongName(String newSongName) {
		this.name.set(newSongName);
	}

	public void setArtist(String newArtist) {
		this.artist.set(newArtist);
	}

	public void setAlbum(String newAlbum) {
		this.album.set(newAlbum);
	}

	public void setYear(String newYear) {
		this.year.set(newYear);
	}

	// utility methods
	public String toString() {
		return "song: " + name + " artist: " + artist;
	}

	@Override
	public int compareTo(Song o) {
		// TODO Auto-generated method stub
		int c = this.name.getValue().compareToIgnoreCase(o.name.getValue());
		if (c != 0)
			return c;

		c = this.artist.getValue().compareToIgnoreCase(o.artist.getValue());
		if (c != 0)
			return c;

		c = this.album.getValue().compareToIgnoreCase(o.album.getValue());
		if (c != 0)
			return c;

		c = this.year.getValue().compareToIgnoreCase(o.year.getValue());
		return c;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Song))
			return false;
		Song temp = (Song) o;
		return ((this.getSongName().equals(temp.getSongName())))
				&& (this.getArtist().equals(temp.getArtist()));
	}

}
