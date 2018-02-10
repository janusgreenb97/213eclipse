package songlib.view;

import java.util.Optional;

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
			songTable.getItems().contains(new Song("","",""));
		}
	}

	public void showSongDetails(Song song) {
		if (song != null) {
			nameLabel.setText(song.getSongName());
			artistLabel.setText(song.getArtist());
			albumLabel.setText(song.getAlbum());

			int y = song.getYear(); 
			if(-1 == y) 
				yearLabel.setText("unknown");
			else
				yearLabel.setText(y+"");
			
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
		// TODO:alert for confirmation, CellEditEvent
		// edit mode: cannot make selection, menuButton is disabled
		Song selectedSong = songTable.getSelectionModel().getSelectedItem();
		TableViewSelectionModel<Song> currentSelection = songTable.getSelectionModel();
		songTable.setSelectionModel(null);

		setEditMode(true);

		nameField.setText(selectedSong.getSongName());

		confirmButton.setOnAction((event) -> {
			// TODO: confirm event: check(is valid?), save, resort
			System.out.println("Button Action: confirm pressed");
		});
		cancelButton.setOnAction((event) -> {
			System.out.println("Button Action: cancel pressed");
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
	}
	
	private boolean isInputValid() {
		return false;
	}

}
