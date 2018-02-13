package songlib.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import songlib.app.SongLib;
import songlib.model.Song;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
import songlib.model.Song;

public class songlibController {

	@FXML
	private TableView<Song> songTable;
	@FXML
	private AnchorPane detailPane;
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
	private TextField artistField;
	@FXML
	private TextField albumField;
	@FXML
	private TextField yearField;
	@FXML
	private Button confirmButton;
	@FXML
	private Button cancelButton;
	@FXML
	private MenuButton menuButton;

	// Reference to the main application.
	private SongLib songlib;

	// controller's constructor
	public songlibController() {

	}

	@FXML
	private void initialize() {

		// Initialize the song table with the two columns.
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
		showSongDetails(null);
		songTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showSongDetails(newValue));

	}

	private void loadLocal() {
		// read from file
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader("./src/songlib/model/data.txt");

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String readName = "", readArtist = "", readAlbum = "", readYear = "";
			while ((readName = bufferedReader.readLine()) != null) {
				if ((readArtist = bufferedReader.readLine()) == null)
					break;
				if ((readAlbum = bufferedReader.readLine()) == null)
					break;
				if ((readYear = bufferedReader.readLine()) == null)
					break;
				Song newSong = new Song(readName, readArtist, readAlbum, readYear);
				// System.err.println(songlib.getSongData());
				songlib.getSongData().add(newSong);
			}

			// Always close files.
			bufferedReader.close();
		} catch (IOException ex) {
			System.err.println("Error reading file '" + "data");
		}
	}

	private void saveLocal() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("./src/songlib/model/data.txt"));
			for (Song a : songlib.getSongData()) {
				System.out.print(a.toString());
				writer.write(a.toString());
			}
			writer.close();
		} catch (IOException ex) {
			System.err.println("error!");
		}
		// save after each revise
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param songLib
	 */
	public void setMainApp(SongLib songlib) {
		this.songlib = songlib;

		loadLocal();

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
			yearLabel.setText(song.getYear());

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
		int selectedIndex = songTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete?");
			alert.initOwner(songlib.getPrimaryStage());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK) {
				return;
			}
			songTable.getItems().remove(selectedIndex);
			songTable.getSelectionModel().select(selectedIndex);
			saveLocal();
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
		// TODO:alert for confirmation, check valid, CellEditEvent
		// edit mode: cannot make selection, menuButton is disabled
		Song selectedSong = songTable.getSelectionModel().getSelectedItem();
		TableViewSelectionModel<Song> currentSelection = songTable.getSelectionModel();
		songTable.setSelectionModel(null);

		setEditMode(true);

		nameField.setText(selectedSong.getSongName());
		artistField.setText(selectedSong.getArtist());
		albumField.setText(selectedSong.getAlbum());
		yearField.setText(selectedSong.getYear());

		confirmButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to add?");
			alert.initOwner(songlib.getPrimaryStage());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK) {
				return;
			}
			String inputName = nameField.getText();
			String inputArtist = artistField.getText();
			String inputAlbum = albumField.getText();
			String inputYear = yearField.getText();

			if (inputName.equalsIgnoreCase(selectedSong.getSongName())
					&& inputArtist.equalsIgnoreCase(selectedSong.getArtist())) {
				// revise selectedSong
				selectedSong.setSongName(inputName);
				selectedSong.setArtist(inputArtist);
				selectedSong.setAlbum(inputAlbum);
				selectedSong.setYear(inputYear);
				songTable.setSelectionModel(currentSelection);
				songTable.getSelectionModel().select(selectedSong);
				showSongDetails(selectedSong);
				saveLocal();
			} else {
				// created new object, check isContains, delete Selected song, add new Song.
				if (inputIsNull()) {
					// error alert
					alert = new Alert(AlertType.WARNING);
					alert.initOwner(songlib.getPrimaryStage());
					alert.setTitle("Error");
					alert.setHeaderText("Name and Artist cannot be empty.");
					alert.setContentText("Please make sure to enter both name and artist!");
					alert.showAndWait();
					return;
				}
				Song newSong = new Song(inputName, inputArtist, inputAlbum, inputYear);
				if (songTable.getItems().contains(newSong)) {
					// newSong = null;
					// error alert
					alert = new Alert(AlertType.WARNING);
					alert.initOwner(songlib.getPrimaryStage());
					alert.setTitle("Error");
					alert.setHeaderText("Duplicated item");
					alert.setContentText("Please enter a different one.");
					alert.showAndWait();
					return;
				}
				songTable.getItems().remove(selectedSong);
				songTable.getItems().add(newSong);
				FXCollections.sort(songTable.getItems());
				songTable.setSelectionModel(currentSelection);
				songTable.getSelectionModel().select(newSong);
				showSongDetails(newSong);
				saveLocal();
			}
			setEditMode(false);
			return;
		});
		cancelButton.setOnAction((event) -> {
			setEditMode(false);
			songTable.setSelectionModel(currentSelection);
		});

	}

	private void setEditMode(Boolean set) {
		confirmButton.setVisible(set);
		cancelButton.setVisible(set);
		menuButton.setDisable(set);
		nameLabel.setVisible(!set);
		nameField.setVisible(set);
		artistLabel.setVisible(!set);
		artistField.setVisible(set);
		albumLabel.setVisible(!set);
		albumField.setVisible(set);
		yearLabel.setVisible(!set);
		yearField.setVisible(set);
	}

	private boolean isInputValid() {
		return false;
	}

	@FXML
	private void handleAddSong() {

		Song selectedSong = songTable.getSelectionModel().getSelectedItem();
		TableViewSelectionModel<Song> currentSelection = songTable.getSelectionModel();
		// TODO: how to fix selection? Don't want the user to random click.
		// songTable.setSelectionModel(null);
		setEditMode(true);
		nameField.setText("");
		artistField.setText("");
		albumField.setText("");
		yearField.setText("");

		confirmButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to add?");
			alert.initOwner(songlib.getPrimaryStage());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK) {
				return;
			}

			if (inputIsNull()) {
				// error alert
				alert = new Alert(AlertType.WARNING);
				alert.initOwner(songlib.getPrimaryStage());
				alert.setTitle("Error");
				alert.setHeaderText("Name and Artist cannot be empty.");
				alert.setContentText("Please make sure to enter both name and artist!");
				alert.showAndWait();
				return;
			}
			// create Song object, compare, new song index?
			Song newSong = new Song(nameField.getText(), artistField.getText(), albumField.getText(),
					yearField.getText());
			// compare
			if (songTable.getItems().contains(newSong)) {
				// newSong = null;
				// error alert
				alert = new Alert(AlertType.WARNING);
				alert.initOwner(songlib.getPrimaryStage());
				alert.setTitle("Error");
				alert.setHeaderText("Duplicated item");
				alert.setContentText("Please enter a different one.");
				alert.showAndWait();
				return;
			}

			songTable.getItems().add(newSong);
			FXCollections.sort(songTable.getItems());
			songTable.getSelectionModel().select(newSong);
			// quit edit mode
			setEditMode(false);
			saveLocal();
		});
		cancelButton.setOnAction((event) -> {
			System.out.println("Button Action: cancel pressed");
			setEditMode(false);
			songTable.setSelectionModel(currentSelection);
		});

	}

	private boolean inputIsNull() {
		return (nameField.getText().length() == 0 || artistField.getText().length() == 0);
	}

}
