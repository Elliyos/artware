package downey.gui;

import downey.main.DataStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class AddInfoController {

	private final ControlledVocab vocab = ControlledVocab.getControlledVocab();
	@FXML
	private TreeItem<String> home = new TreeItem<String>("Home");
	@FXML
	private Button submit, goBack, editChoices, removeChoices;
	@FXML
	private TextField nameInput;
	@FXML
	private ChoiceBox<String> cultureInput, occupationInput, genderInput;
	@FXML
	private TextArea bioInput;

	public AddInfoController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		genderInput.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
		cultureInput.setItems(vocab.getCultureOptions());
		occupationInput.setItems(vocab.getOccupationOptions());
		choicesAction();
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;

		if (event.getSource() == submit) {
			add();
			stage = (Stage) submit.getScene().getWindow();
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

	/**
	 * Stores everything the user entered and creates a person.
	 * 
	 * @throws IOException
	 */
	public void add() throws IOException {
		DataStorage DS = DataStorage.getMainDataStorage();
		String name = nameInput.getText();
		String culture = (String) cultureInput.getValue();
		String occupation = (String) occupationInput.getValue();
		String gender = (String) genderInput.getValue();
		String bio = bioInput.getText();

		DS.addPerson(name, culture, occupation, gender, bio);
		DS.savePeople();
		DS.saveConnections();
	}

}
