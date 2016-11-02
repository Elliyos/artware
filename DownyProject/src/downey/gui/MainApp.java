/**
 * Progress: WIP 
 */

package downey.gui;

import downey.main.*;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ArtWARE");

        initialize();
    }

    public Stage getPrimaryStage() {
    	return primaryStage;
    }
    /**
     * Initializes the root layout.
     */
    public void initialize() {
        try {
            // Load root layout from fxml file.
        	 FXMLLoader loader = new FXMLLoader();
             loader.setLocation(MainApp.class.getResource("MainMenu.fxml"));
             AnchorPane mainMenu = (AnchorPane) loader.load();

            Scene scene = new Scene(mainMenu);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
    	DataStorage.getMainDataStorage().loadPeople();
    	DataStorage.getMainDataStorage().loadConnections();
    	ControlledVocab.getControlledVocab().loadControlledVocab();
        launch(args);
    }
}