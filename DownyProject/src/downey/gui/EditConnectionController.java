package downey.gui;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JList;

import downey.main.DataStorage;
import downey.main.Person;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditConnectionController {

	private DataStorage DS = DataStorage.getMainDataStorage();
	private ArrayList<Person> peopleList = DS.getPeopleArray();
	private ArrayList<Person> recipientsList;
	private MainApp mainApp;
	
	@FXML
	private Button submit;
	@FXML
	private Button goBack;
	@FXML
	private ComboBox<Person> sender;
	@FXML
	private ComboBox<Person> recipients;
	@FXML
	private TextField dateInput;
	@FXML
	private TextField typeInput;
	@FXML
	private TextField locationInput;
	@FXML
	private TextField citationInput;
	@FXML
	private TextArea notes;

	public EditConnectionController() {
	}
	
	public String[] getInfo(){
		String[] infoArray = new String[7];
		infoArray[0] = sender.getSelectionModel().getSelectedItem().getName();
		infoArray[1] = recipients.getSelectionModel().getSelectedItem().getName();
		infoArray[2] = dateInput.getText();
		infoArray[3] = typeInput.getText();
		infoArray[4] = locationInput.getText();
		infoArray[5] = citationInput.getText();
		infoArray[6] = notes.getText();
		return infoArray;
	}

	@FXML
	private void initialize() { //THIS ALL NEEDS CHANGED
		sender.setItems(FXCollections.observableArrayList(peopleList));
		recipients.setItems(FXCollections.observableArrayList(peopleList));
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == this.submit) {
			String[] info = getInfo();
			DS.getSelectedConnection().editConnection(DS.getPersonObject(info[0]), DS.convertToPersonArray(info[1]) ,info[2],info[3],info[4],info[5],info[6]);
			DS.saveConnections();
			stage = (Stage) this.submit.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else {
			stage = (Stage) this.goBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ConnectionOptions.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	

	@FXML
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
