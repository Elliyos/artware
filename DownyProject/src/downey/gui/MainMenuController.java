package downey.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class MainMenuController {

	@FXML
	private Button peopleAdd, peopleView, connectionsAdd, connectionsView, exportGephi, exportPalladio;

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
	private void initialize() {

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
		} else {
			stage = (Stage) this.connectionsView.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ViewConnections.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}