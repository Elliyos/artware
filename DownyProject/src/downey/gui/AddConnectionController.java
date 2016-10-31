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

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private final ControlledVocab vocab = ControlledVocab.getControlledVocab(); 
	private MainApp mainApp;
	public ArrayList<Person> peopleList = DS.getPeopleArray();

	@FXML
	private Button submit, goBack, add, search, remove, clear;
	@FXML
	private ChoiceBox<String> initiator, typeInput;
	@FXML
	private DatePicker dateInput; 
	@FXML
	private TextField locationInput, citationInput, searchInput;
	@FXML
	private TextArea notes;

	private final ObservableSet<String> observableSet = FXCollections.observableSet();
	private ObservableSet<String> filteredSet = FXCollections.observableSet();
	@FXML
	ListView<String> recipientList = new ListView<String>();
	@FXML
	ListView<String> selectedRecipientList = new ListView<>();

	private Person selectedPerson;
	private final ArrayList<Person> selectedRecipients = new ArrayList<>();
	
	public AddConnectionController() {}

	@FXML
	private void initialize() {
		recipientList.setItems(FXCollections.observableArrayList(nameList()));
		recipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		selectedRecipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		initiator.setItems(FXCollections.observableArrayList(nameList()));
		typeInput.setItems(vocab.getInteractionTypeOptions());
		addRecipients();
		removeRecipients();
		searchRecipients();
		clearRecipients();
	}

	public ObservableSet<String> nameList() {
		for (int i = 0; i <= peopleList.size() - 1; i++) {
			selectedPerson = peopleList.get(i);
			observableSet.addAll(Arrays.asList(selectedPerson.getName()));
		}
		return observableSet;
	}
	
	public ObservableSet<String> filteredNameList(PersonQuery query) {
		for (int i = 0; i <= peopleList.size() - 1; i++) { 
			if (query.accepts(peopleList.get(i))) {
				selectedPerson = peopleList.get(i);
				String name = selectedPerson.getName();
				filteredSet.addAll(Arrays.asList(name));
			}
		}
		return filteredSet;
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		
		if (event.getSource() == this.submit) {
			createConnection();
			stage = (Stage) this.submit.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ViewConnections.fxml"));
		} else {
			stage = (Stage) this.goBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void addRecipients() {
		add.setOnAction((event) -> {
			observableSet.clear();
			ObservableList<String> selectedRecipientsTemp = recipientList.getSelectionModel().getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				if (!selectedRecipientList.getItems().contains(selectedRecipientsTemp.get(i))) {
					selectedRecipientList.getItems().add(i, selectedRecipientsTemp.get(i));
				}
			}
		});
	}
	
	public void searchRecipients() {
		search.setOnAction((e) -> {
			filteredSet.clear();
			PersonQuery containsFilter = new PersonContainsQuery(searchInput.getText(), "Name");
			recipientList.setItems(FXCollections.observableArrayList(filteredNameList(containsFilter)));
		});
	}
	
	public void removeRecipients() {
		remove.setOnAction((event) -> {
			ObservableList<String> selectedRecipientsTemp = selectedRecipientList.getSelectionModel().getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				selectedRecipientList.getItems().remove(selectedRecipientsTemp.get(i));
			}
		});
	}
	
	public void clearRecipients() {
		clear.setOnAction(e -> recipientList.setItems(FXCollections.observableArrayList(nameList())));
	}

	public void createConnection() throws IOException{
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
	}
	
	@FXML
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}