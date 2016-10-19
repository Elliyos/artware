package downey.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import downey.main.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddConnectionController {

	private DataStorage DS = DataStorage.getMainDataStorage();
	private ArrayList<Person> peopleList = DS.getPeopleArray();
	private MainApp mainApp;

	@FXML
	private Button submit;
	@FXML
	private Button goBack;
	@FXML
	private ChoiceBox<String> initiator;
	@FXML
	private DatePicker dateInput; 
	@FXML
	private ChoiceBox<String> typeInput;
	@FXML
	private TextField locationInput;
	@FXML
	private TextField citationInput;
	@FXML
	private TextArea notes;
	@FXML
	private ObservableSet<String> observableSet = FXCollections.observableSet();
	@FXML
	ObservableList<String> people = FXCollections.observableArrayList();
	@FXML
	ListView<String> recipients = new ListView<String>(people);

	private Person selectedPerson;
	private ArrayList<Person> selectedRecipients = new ArrayList<>();
	public AddConnectionController() {

	}

	@FXML
	private void initialize() {
		recipients.setItems(FXCollections.observableArrayList(nameList(peopleList)));
		recipients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		initiator.setItems(FXCollections.observableArrayList(nameList(peopleList)));
		typeInput.setItems(FXCollections.observableArrayList("Letter", "Email", "Meeting", "Party"));
	}

	public ObservableSet<String> nameList(ArrayList<Person> peopleList) {
		String name = "";
		for (int i = 0; i <= peopleList.size() - 1; i++) {
			selectedPerson = peopleList.get(i);
			name = selectedPerson.getName();
			observableSet.addAll(Arrays.asList(name));
		}
		return observableSet;
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == this.submit) {
			ObservableList<String> selectedRecipientsTemp = recipients.getSelectionModel().getSelectedItems();
			String initiatorPerson = initiator.getValue();
			Person sender = null;
			if (initiatorPerson != null){
				sender = DS.getPersonObject(initiatorPerson);
			}
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				String tempName = selectedRecipientsTemp.get(i);
//				DS.storeSelectedName(selectedRecipientsTemp.get(i));
//				Person tempPerson = DS.getPersonObject(DS.getSelectedName());
				selectedRecipients.add(DS.getPersonObject(tempName));
			}
			String location = locationInput.getText();
			if (location.equals("")) location = "Unknown";
			DS.addConnection(sender, selectedRecipients, dateInput.getValue().toString(), typeInput.getValue(),
					location, citationInput.getText(), notes.getText());
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
