package songlib.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import songlib.app.SongLib;
import songlib.model.Song;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import songlib.model.Song;

public class songlibController {

	@FXML
	private TableView<Song> songTable;
	@FXML
	private TableColumn<Song, String> nameColumn;
	@FXML
	private TableColumn<Song, String> artistColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label artistLabel;
	@FXML
	private Label albumLabel;
	@FXML
	private Label yearLabel;
	@FXML
	private TextField nameField;
	@FXML
	private Button confirmButton;
	@FXML
	private Button cancelButton;

	// Reference to the main application.
	private SongLib songlib;

	// controller's constructor
	public songlibController() {

	}

	@FXML
	private void initialize() {

		// Initialize the person table with the two columns.
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
		showSongDetails(null);
		songTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showSongDetails(newValue));

		// TODO: when the app is first open, the first song need to be selected?

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param songLib
	 */
	public void setMainApp(SongLib songlib) {
		this.songlib = songlib;

		// Add observable list data to the table
		songTable.setItems(songlib.getSongData());

		if (songTable.getItems().size() > 0) {

			songTable.getSelectionModel().select(0);
		}
	}

	public void showSongDetails(Song song) {
		if (song != null) {
			nameLabel.setText(song.getSongName());
			artistLabel.setText(song.getArtist());
			albumLabel.setText(song.getAlbum());
			yearLabel.setText(Integer.toString(song.getYear()));
		} else {
			nameLabel.setText("");
			artistLabel.setText("");
			albumLabel.setText("");
			yearLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteSong() {
		// TODO: alert for confirmation
		int selectedIndex = songTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			songTable.getItems().remove(selectedIndex);
			songTable.getSelectionModel().select(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(songlib.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Song Selected");
			alert.setContentText("Please select a song in the table.");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleEditSong() {
		// TODO:alert for confirmation
		Song selectedSong = songTable.getSelectionModel().getSelectedItem();
		confirmButton.setVisible(true);
		cancelButton.setVisible(true);

		nameLabel.setVisible(false);
		nameField.setVisible(true);
		nameField.setText(selectedSong.getSongName());

	}

}
