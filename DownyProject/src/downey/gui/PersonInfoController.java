package downey.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import downey.main.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private final ArrayList<Connection> connectionList = DS.getConnectionArray();
	private Person chosenPerson = DS.getPersonObject(SelectedInformationTracker.getSelectedName());
	private String chosenPeople;

	@FXML
	private Button home, editButton, toViewPeople;
	@FXML
	private Label nameLabel, nicknameLabel, occupationLabel, cultureLabel, genderLabel;
	@FXML
	private TextArea bioArea;
	@FXML
	ListView<String> connections = new ListView<>();
	@FXML
	private ObservableList<String> observableConnectionList = FXCollections.observableArrayList();

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
		nameLabel.setText(chosenPerson.getName());
		nicknameLabel.setText(chosenPerson.getNickname());
		occupationLabel.setText(chosenPerson.getOccupation());
		cultureLabel.setText(chosenPerson.getCulture());
		genderLabel.setText(chosenPerson.getGender());
		bioArea.setText(chosenPerson.getBio());
		connections.setItems(getConnectionList());
	}

	/**
	 * Gets a list of existing, archived connections.
	 * @return
	 */
	private ObservableList<String> getConnectionList() {
		for (int i = 0; i <= connectionList.size() - 1; i++) {
			if (connectionList.get(i).getSender() != null) {
				chosenPerson = connectionList.get(i).getSender();
				chosenPeople = connectionList.get(i).getReceiverNameList().toString();
				observableConnectionList.addAll(Arrays.asList(chosenPerson.getName() + ": " + chosenPeople));
			} else {
				chosenPeople = connectionList.get(i).getReceiverNameList().toString();
				observableConnectionList.addAll(Arrays.asList(" : " + chosenPeople));
			}
		}
		return observableConnectionList;
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
		stage.setResizable(false);
		stage.show();
	}
}
