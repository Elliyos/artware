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

	private final ControlledVocab vocab = ControlledVocab.getControlledVocab(); 
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
		cultureInput.setItems(vocab.getCultureOptions());
		occupationInput.setItems(vocab.getOccupationOptions());
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;

		if (event.getSource() == add) {
			add();
			stage = (Stage) add.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("FindPerson.fxml"));
		} else {
			stage = (Stage) goBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Stores everything the user entered and creates a person.
	 * @throws IOException
	 */
	public void add() throws IOException{
		DataStorage DS = DataStorage.getMainDataStorage();
		String name = nameInput.getText();
		String culture = cultureInput.getValue();
		String occupation = occupationInput.getValue();
		String gender = genderInput.getValue();
		String bio = bioInput.getText();

		DS.addPerson(name, culture, occupation, gender, bio);
		DS.savePeople();
		DS.saveConnections();
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
