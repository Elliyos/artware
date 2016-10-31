package downey.gui;

import java.io.IOException;
import java.util.ArrayList;

import downey.main.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PersonInfoController {

	private MainApp mainApp;
	// private Person selectedPerson = super.getSelectedPerson();
	private final DataStorage DS = DataStorage.getMainDataStorage();

	@FXML
	private Button home, editButton, toViewPeople;
	@FXML
	private Label nameLabel, occupationLabel, cultureLabel, genderLabel;
	@FXML
	private TextArea bioArea;
	@FXML
	ListView<String> connections = new ListView<>();

	public PersonInfoController() {
	}

	/**
	 * This method is currently bugged. Index isn't returning correctly;
	 * therefore, only one person is being included to show core functionality
	 * of information page.
	 * 
	 * @throws IOException
	 */
	@FXML
	private void initialize() throws IOException {
		Person chosenPerson = DS.getPersonObject(SelectedInformationTracker.getSelectedName());
		ArrayList<String> personConnections = DS.getConnectionsForPerson(chosenPerson.getName());
		nameLabel.setText(chosenPerson.getName());
		occupationLabel.setText(chosenPerson.getOccupation());
		cultureLabel.setText(chosenPerson.getCulture());
		genderLabel.setText(chosenPerson.getGender());
		bioArea.setText(chosenPerson.getBio());
		connections.setItems(FXCollections.observableArrayList(personConnections));
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == home) {
			stage = (Stage) home.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else if (event.getSource() == editButton) {
			stage = (Stage) editButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("EditInfo.fxml"));
		} else {
			stage = (Stage) toViewPeople.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("FindPerson.fxml"));
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
