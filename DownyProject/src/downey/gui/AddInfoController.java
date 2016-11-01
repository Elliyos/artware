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
				ChoiceDialog<String> addDialog = new ChoiceDialog<>(choices.get(0), choices);
				addDialog.setTitle("Add Field Choice");
				addDialog.setHeaderText(null);
				addDialog.setContentText("Choose field to add choice to: ");
				Optional<String> chosenField = addDialog.showAndWait();
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
				ChoiceDialog<String> removeDialog = new ChoiceDialog<>(choices.get(0), choices);
				removeDialog.setTitle("Remove Field Choice");
				removeDialog.setHeaderText(null);
				removeDialog.setContentText("Choose field to remove choice from: ");
				Optional<String> chosenField = removeDialog.showAndWait();
				if (chosenField.isPresent())
					if (chosenField.get().equals(choices.get(0))) {
						List<String> fieldList = vocab.getOccupationOptions();
						ChoiceDialog<String> dialog = new ChoiceDialog<>(fieldList.get(0), fieldList);
						dialog.setTitle("Remove Choice");
						dialog.setHeaderText(null);
						dialog.setContentText("Choose to remove: ");
						Optional<String> removedChoice = dialog.showAndWait();
						vocab.removeOccupationOption(removedChoice.get());
					} else {
						List<String> fieldList = vocab.getCultureOptions();
						ChoiceDialog<String> dialog = new ChoiceDialog<>(fieldList.get(0), fieldList);
						dialog.setTitle("Remove Choice");
						dialog.setHeaderText(null);
						dialog.setContentText("Choose to remove: ");
						Optional<String> removedChoice = dialog.showAndWait();
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
