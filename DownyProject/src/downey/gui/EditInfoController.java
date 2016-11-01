package downey.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import downey.main.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;

public class EditInfoController {

	private final ControlledVocab vocab = ControlledVocab.getControlledVocab(); 
	@FXML
	private TextField nameInput;
	@FXML
	private ChoiceBox<String> occupationInput, cultureInput, genderInput;
	@FXML
	private TextArea bioInput;
	@FXML
	private Button submit, home, toViewPeople, toPersonInfo, editChoices;

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private MainApp mainApp;
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
		choicesAction();
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
	
	private Optional<String> choiceDialog(List<String> choices) {
		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Edit Field");
		dialog.setHeaderText(null);
		dialog.setContentText("Choose your option");
		Optional<String> result = dialog.showAndWait();
		return result;
	}
	
	@FXML
	private void choicesAction() {
		List<String> choices = Arrays.asList("Occupation", "Culture");
		editChoices.setOnAction((event) -> {
			Alert editChoices = new Alert(AlertType.CONFIRMATION);
			editChoices.setTitle("Edit Choices for Data Fields");
			editChoices.setHeaderText(null);
			editChoices.setContentText("Choose your option");
			ButtonType addButton = new ButtonType("Add");
			ButtonType removeButton = new ButtonType("Remove");
			ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			editChoices.getButtonTypes().setAll(addButton, removeButton, cancelButton);
			Optional<ButtonType> result = editChoices.showAndWait();
			if (result.get() == addButton) {
				Optional<String> chosenField = choiceDialog(choices);
				TextInputDialog input = new TextInputDialog();
				input.setTitle("Text Input Dialog");
				input.setHeaderText(null);
				input.setContentText("Enter new choice:");
				Optional<String> addedChoice = input.showAndWait();
				if (addedChoice.isPresent() && !addedChoice.get().equals("")) {
					if (chosenField.get().equals(choices.get(0))) {
						vocab.addOccupationOption(addedChoice.get());
					} else {
						vocab.addCultureOption(addedChoice.get());
					}
				}
			} else if (result.get() == removeButton) {
				Optional<String> chosenField = choiceDialog(choices);
				if (chosenField.isPresent())
					if (chosenField.get().equals(choices.get(0))) {
						List<String> fieldList = vocab.getOccupationOptions();
						Optional<String> removedChoice = choiceDialog(fieldList);
						vocab.removeOccupationOption(removedChoice.get());
					} else {
						List<String> fieldList = vocab.getCultureOptions();
						Optional<String> removedChoice = choiceDialog(fieldList);
						vocab.removeCultureOption(removedChoice.get());
					}
			} else {
			}
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
