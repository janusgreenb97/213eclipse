package songlib.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import songlib.model.Song;
import songlib.view.songlibController;

public class SongLib extends Application {
	private Stage primaryStage;
	/*
	 * The data as an observable list of Songs.
	 */
	private ObservableList<Song> songData = FXCollections.observableArrayList();

	/**
	 * Constructor
	 */
	public SongLib() {
		// Add some sample data
		
		songData.add(new Song("Strawberry Field", "Betles"));
		songData.add(new Song("222", "Betles"));
		songData.add(new Song("333", "Betles"));
		
	}

	/**
	 * Returns the data as an observable list of Persons.
	 * 
	 * @return
	 */
	public ObservableList<Song> getSongData() {
		return songData;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// load the songlib.fxml into songOverView
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));
			AnchorPane songOverView = (AnchorPane) loader.load();

			// Give the controller access to the main app: SongLib.
			songlibController controller = loader.getController();
			controller.setMainApp(this);
			
			//set songOverView to be the scene
			Scene scene = new Scene(songOverView);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Song Library");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
