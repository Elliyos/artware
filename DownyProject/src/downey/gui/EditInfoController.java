package downey.gui;

import java.io.IOException;
import java.util.ArrayList;

import downey.main.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class EditInfoController {

	private final ControlledVocab vocab = ControlledVocab.getControlledVocab(); 
	@FXML
	private TextField nameInput;
	@FXML
	private ChoiceBox<String> occupationInput, cultureInput, genderInput;
	@FXML
	private TextArea bioInput;
	@FXML
	private Button submit, home, toViewPeople, toPersonInfo;

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private MainApp mainApp;
	private final ArrayList<Person> peopleList = DS.getPeopleArray();
	private Person currentPerson;

	public EditInfoController() {
	}

	@FXML
	private void initialize() throws IOException {
		currentPerson = DS.getPersonObject(SelectedInformationTracker.getSelectedName());
		nameInput.setText(currentPerson.getName());
		occupationInput.setValue(currentPerson.getOccupation());
		cultureInput.setValue(currentPerson.getCulture());
		bioInput.setText(currentPerson.getBio());
		genderInput.setValue(currentPerson.getGender());
		occupationInput.setItems(vocab.getOccupationOptions());
		cultureInput.setItems(vocab.getCultureOptions());
		genderInput.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == submit) {
			currentPerson.editPerson(nameInput.getText(), cultureInput.getValue(), occupationInput.getValue(),
					genderInput.getValue(), bioInput.getText());
			DS.savePeople();
			DS.saveConnections();
			stage = (Stage) submit.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else if (event.getSource() == home){
			stage = (Stage) home.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else if (event.getSource() == toViewPeople){
			stage = (Stage) toViewPeople.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("FindPerson.fxml"));
		} else {
			stage = (Stage) toPersonInfo.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("PersonInfo.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
