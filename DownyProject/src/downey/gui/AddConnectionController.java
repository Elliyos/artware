package downey.gui;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import downey.main.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddConnectionController {

	private DataStorage DS = DataStorage.getMainDataStorage();
	private MainApp mainApp;

	@FXML
	private Button submit, goBack, add, search, remove;
	@FXML
	private ChoiceBox<String> initiator, typeInput;
	@FXML
	private DatePicker dateInput; 
	@FXML
	private TextField locationInput, citationInput, searchInput;
	@FXML
	private TextArea notes;
	@FXML
	private ObservableSet<String> observableSet = FXCollections.observableSet();
	@FXML
	ObservableList<String> people = FXCollections.observableArrayList();
	@FXML
	ListView<String> recipientList = new ListView<String>(people);
	@FXML
	ListView<String> selectedRecipientList = new ListView<>();

	private Person selectedPerson;
	private ArrayList<Person> selectedRecipients = new ArrayList<>();
	
	public AddConnectionController() {}

	@FXML
	private void initialize() {
		recipientList.setItems(FXCollections.observableArrayList(nameList(DS.getPeopleArray())));
		recipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		selectedRecipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		initiator.setItems(FXCollections.observableArrayList(nameList(DS.getPeopleArray())));
		typeInput.setItems(FXCollections.observableArrayList("Letter", "Email", "Meeting", "Party"));

		search.setOnAction((event) -> {
			String searchText = searchInput.getText();
			observableSet.clear();
			if (DS.searchPerson(searchText) > 0) {
				Person temp = DS.getPersonObject(searchText);
				observableSet.add(temp.getName());
				recipientList.setItems(FXCollections.observableArrayList(observableSet));
			} else if (searchText.equals("")) {
				recipientList.setItems(FXCollections.observableArrayList(nameList(DS.getPeopleArray())));
				recipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			} else {
				recipientList.setItems(FXCollections.observableArrayList(observableSet));
			}
		});
		
		
		add.setOnAction((event) -> {
			observableSet.clear();
			ObservableList<String> selectedRecipientsTemp = recipientList.getSelectionModel().getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				if (!selectedRecipientList.getItems().contains(selectedRecipientsTemp.get(i))) {
					selectedRecipientList.getItems().add(i, selectedRecipientsTemp.get(i));
				}
			}
		});
		
		remove.setOnAction((event) -> {
			ObservableList<String> selectedRecipientsTemp = selectedRecipientList.getSelectionModel().getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				selectedRecipientList.getItems().remove(selectedRecipientsTemp.get(i));
			}
		});
	}
	 

	public ObservableSet<String> nameList(ArrayList<Person> peopleList) {
		for (int i = 0; i <= peopleList.size() - 1; i++) {
			selectedPerson = peopleList.get(i);
			observableSet.addAll(Arrays.asList(selectedPerson.getName()));
		}
		return observableSet;
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		
		if (event.getSource() == this.submit) {
			for (int i = 0; i < selectedRecipientList.getItems().size(); i++) {
				String name = selectedRecipientList.getItems().get(i);
				selectedRecipients.add(DS.getPersonObject(name));
			}
			String location = locationInput.getText();
			if (location.equals("")) location = "Unknown";
			String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			String initiatorName = initiator.getValue();
			if (initiatorName != null){
				Person sender = DS.getPersonObject(initiatorName);
				DS.addConnection(sender, selectedRecipients, date, typeInput.getValue(),
						location, citationInput.getText(), notes.getText());

			} else {
				DS.addGroupConnection(selectedRecipients, date, typeInput.getValue(),
						location, citationInput.getText(), notes.getText());
			}
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