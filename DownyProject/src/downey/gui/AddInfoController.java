package downey.gui;

import downey.main.ControlledVocab;
import downey.main.DataStorage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
	private Button submit, goBack, editChoices, removeChoices, occupationVocabAdd, occupationVocabRemove, cultureVocabAdd, cultureVocabRemove, genderVocabAdd, genderVocabRemove;
	@FXML
	private TextField nameInput, nicknameInput;
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
		genderInput.setItems(vocab.getGenderOptions());
		cultureInput.setItems(vocab.getCultureOptions());
		occupationInput.setItems(vocab.getOccupationOptions());
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
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	private void occupationVocabAdd() {
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
	}
	
	@FXML
	private void occupationVocabRemove() {
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
	}
	
	@FXML
	private void cultureVocabAdd() {
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
	}
	
	@FXML
	private void cultureVocabRemove() {
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
	}
	
	@FXML
	private void genderVocabAdd() {
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
	}
	
	@FXML
	private void genderVocabRemove() {
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
	}

	/**
	 * Stores everything the user entered and creates a person.
	 * 
	 * @throws IOException
	 */
	public void add() throws IOException {
		DataStorage DS = DataStorage.getMainDataStorage();
		String name = nameInput.getText();
		String nickname = nicknameInput.getText();
		String culture = (String) cultureInput.getValue();
		String occupation = (String) occupationInput.getValue();
		String gender = (String) genderInput.getValue();
		String bio = bioInput.getText();

		DS.addPerson(name, nickname, culture, occupation, gender, bio);
		DS.savePeople();
		DS.saveConnections();
	}

}
