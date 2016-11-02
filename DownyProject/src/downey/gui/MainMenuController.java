package downey.gui;

import java.io.File;
import java.io.IOException;

import downey.main.DataStorage;
import downey.main.Exporter;
import downey.main.GephiExporter;
import downey.main.PalladioExporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainMenuController {
	
	@FXML
	private Button peopleAdd, peopleView, connectionsAdd, connectionsView, exportGephi, exportPalladio, about;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */

	public MainMenuController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */

	@FXML
	public void initialize() {
		exportGephi.setOnAction((event)-> { exportToGephi(); });
		exportPalladio.setOnAction((event) -> { exportToPalladio(); });
	}
	
	/**
	 * Chooses a file name to export the archived information.
	 * @return
	 */
	public File getChosenFile(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a file name PREFIX for exporting:");
        File file = fileChooser.showSaveDialog(null);
        return file;
	}
	
	/**
	 * Handles exporting to Gephi.
	 */
	public void exportToGephi() {
		File file = getChosenFile();
		if (file != null) {
			Exporter gephiEx = new GephiExporter(DataStorage.getMainDataStorage().getConnectionArray(),
					DataStorage.getMainDataStorage().getPeopleArray());
			try {
				gephiEx.export(file.getPath());
			} catch (IOException e) {
				//TODO: alert the user if error happens
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Handles exporting to Palladio.
	 */
	public void exportToPalladio() {	
		File file = getChosenFile();
		if (file != null) {
			Exporter palladioEx = new PalladioExporter(DataStorage.getMainDataStorage().getConnectionArray());
			try {
				palladioEx.export(file.getPath());
			} catch (IOException e) {
				//TODO: alert the user if error happens
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == this.peopleAdd) {
			stage = (Stage) this.peopleAdd.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("AddInfo.fxml"));
		} else if (event.getSource() == this.peopleView) {
			stage = (Stage) this.peopleView.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("FindPerson.fxml"));
		} else if (event.getSource() == this.connectionsAdd) {
			stage = (Stage) this.connectionsAdd.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("AddConnection.fxml"));
		} else if (event.getSource() == this.connectionsView){
			stage = (Stage) this.connectionsView.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ViewConnections.fxml"));
		} else {
			stage = (Stage) this.about.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("About.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}