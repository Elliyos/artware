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
	private JComboBox<Person> recipients;
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
		String[] temp = new String[7];
		temp[0] = sender.getSelectionModel().getSelectedItem().getName();
		//temp[1] = recipients.getSelectionModel().getSelectedItem().getName();
		temp[2] = dateInput.getText();
		temp[3] = typeInput.getText();
		temp[4] = locationInput.getText();
		temp[5] = citationInput.getText();
		temp[6] = notes.getText();
		return temp;
	}

	@FXML
	private void initialize() { //THIS ALL NEEDS CHANGED
		sender.setItems(FXCollections.observableArrayList(peopleList));
		//recipients.setItems(FXCollections.observableArrayList(peopleList));
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == this.submit) {
			String[] stuff = getInfo();
			DS.addConnection(DS.getPersonObject(stuff[0]), DS.convertToPersonArray(stuff[1]) ,stuff[2],stuff[3],stuff[4],stuff[5],stuff[6]);
			DS.saveConnections("connections");
			//recipientsList.add(peopleList.get(1)); //CHANGE THIS
			//PUT EDIT CONNECTION CODE HERE
			//DS.saveConnections("connections");
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
