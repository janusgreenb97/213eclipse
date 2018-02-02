package songlib.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import songlib.app.SongLib;
import songlib.model.Song;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import songlib.model.Song;

public class songlibController {
	@FXML
	ListView<Song> listView;
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
	}
	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(SongLib songlib) {
        this.songlib = songlib;

        // Add observable list data to the table
        songTable.setItems(songlib.getSongData());
    }
	public void start(Stage mainStage) {

	}
}
