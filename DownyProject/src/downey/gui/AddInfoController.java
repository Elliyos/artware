package downey.gui;

import downey.main.DataStorage;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class AddInfoController {

	@FXML
	private Button add, goBack;
	@FXML
	private TextField nameInput;
	@FXML
	private ChoiceBox<String> cultureInput, occupationInput, genderInput;
	@FXML
	private TextArea bioInput;
	private MainApp mainApp;

	public AddInfoController() {}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */

	@FXML
	private void initialize() {
		genderInput.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
		cultureInput.setItems(FXCollections.observableArrayList("American", "Italian", "French", "German"));
		occupationInput.setItems(FXCollections.observableArrayList("Sculptor", "Scholar", "Painter", "Writer"));
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;

		if (event.getSource() == add) {
			DataStorage DS = DataStorage.getMainDataStorage();
			String name = nameInput.getText();
			String culture = cultureInput.getValue();
			String occupation = occupationInput.getValue();
			String gender = genderInput.getValue();
			String bio = bioInput.getText();

			DS.addPerson(name, culture, occupation, gender, bio);
			DS.savePeople();
			DS.saveConnections();

			// get reference to the button's stage
			stage = (Stage) add.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else {
			stage = (Stage) goBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ProfileOptions.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	@FXML
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
