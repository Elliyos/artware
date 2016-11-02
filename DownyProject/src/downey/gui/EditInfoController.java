package downey.gui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import downey.main.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;

public class EditInfoController {

	private final ControlledVocab vocab = ControlledVocab.getControlledVocab(); 
	@FXML
	private TextField nameInput, nicknameInput;
	@FXML
	private ChoiceBox<String> occupationInput, cultureInput, genderInput;
	@FXML
	private TextArea bioInput;
	@FXML
	private Button submit, home, toViewPeople, toPersonInfo, occupationVocabAdd, occupationVocabRemove, cultureVocabAdd, cultureVocabRemove, genderVocabAdd, genderVocabRemove;

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private Person currentPerson;

	public EditInfoController() {
	}

	@FXML
	private void initialize() throws IOException {
		currentPerson = DS.getPersonObject(SelectedInformationTracker.getSelectedName());
		nameInput.setText(currentPerson.getName());
		nicknameInput.setText(currentPerson.getNickname());
		occupationInput.setValue(currentPerson.getOccupation());
		cultureInput.setValue(currentPerson.getCulture());
		bioInput.setText(currentPerson.getBio());
		genderInput.setValue(currentPerson.getGender());
		occupationInput.setItems(vocab.getOccupationOptions());
		cultureInput.setItems(vocab.getCultureOptions());
		genderInput.setItems(vocab.getGenderOptions());
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == submit) {
			currentPerson.editPerson(nameInput.getText(), nicknameInput.getText(), cultureInput.getValue(), occupationInput.getValue(),
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
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	private void occupationVocabAdd() {
		occupationVocabAdd.setOnAction(e -> {
			TextInputDialog input = new TextInputDialog();
			input.setTitle("Add Occupation");
			input.setHeaderText(null);
			input.setContentText("Enter a new occupation:");
			Optional<String> addedChoice = input.showAndWait();
			if (addedChoice.isPresent() && !addedChoice.get().equals("")) {
				vocab.addOccupationOption(addedChoice.get());
			}
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	private void occupationVocabRemove() {
		occupationVocabRemove.setOnAction(e -> {
			List<String> choices = vocab.getOccupationOptions();

			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Remove an Occupation");
			dialog.setHeaderText(null);
			dialog.setContentText("Choose an occupation to remove:");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
			    vocab.removeOccupationOption(result.get());
			}
			
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	private void cultureVocabAdd() {
		cultureVocabAdd.setOnAction(e -> {
			TextInputDialog input = new TextInputDialog();
			input.setTitle("Add Culture");
			input.setHeaderText(null);
			input.setContentText("Enter a new culture:");
			Optional<String> addedChoice = input.showAndWait();
			if (addedChoice.isPresent() && !addedChoice.get().equals("")) {
				vocab.addCultureOption(addedChoice.get());
			}
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	private void cultureVocabRemove() {
		cultureVocabRemove.setOnAction(e -> {
			List<String> choices = vocab.getCultureOptions();

			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Remove a Culture");
			dialog.setHeaderText(null);
			dialog.setContentText("Choose a culture to remove:");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
			    vocab.removeCultureOption(result.get());
			}
			
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	private void genderVocabAdd() {
		genderVocabAdd.setOnAction(e -> {
			TextInputDialog input = new TextInputDialog();
			input.setTitle("Add Gender");
			input.setHeaderText(null);
			input.setContentText("Enter a new gender:");
			Optional<String> addedChoice = input.showAndWait();
			if (addedChoice.isPresent() && !addedChoice.get().equals("")) {
				vocab.addGenderOption(addedChoice.get());
			}
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	private void genderVocabRemove() {
		genderVocabRemove.setOnAction(e -> {
			List<String> choices = vocab.getGenderOptions();

			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Remove a Gender");
			dialog.setHeaderText(null);
			dialog.setContentText("Choose a gender to remove:");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
			    vocab.removeGenderOption(result.get());
			}
			
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
}
