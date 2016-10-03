package downey.gui;

import downey.main.DataStorage;
import downey.main.Person;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddInfoController {

	@FXML
	private Button add;
	@FXML
	private Button goBack;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField cultureInput;
	@FXML
	private TextField occupationInput;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox<String> genderInput;
	@FXML
	private TextArea bioInput;
	private MainApp mainApp;

	public AddInfoController() {
		// GOTTA FIGURE OUT HOW TO INITIALIZE THIS
		// genderInput.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */

	@FXML
	private void initialize() {
		genderInput.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;

		if (event.getSource() == add) {

			DataStorage DS = new DataStorage();
			String name = nameInput.getText();
			String culture = cultureInput.getText();
			String occupation = occupationInput.getText();
			String gender = genderInput.getValue();
			String bio = bioInput.getText();
			String save;
			Person person;

			DS.addPerson(name, culture, occupation, gender, bio);
			DS.loadPeople("people");
			DS.savePeople("people");

			// get reference to the button's stage
			stage = (Stage) add.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(getClass().getResource("AddPersonThanks.fxml"));
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
