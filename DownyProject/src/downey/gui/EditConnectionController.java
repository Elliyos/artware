package downey.gui;

import java.io.IOException;
import java.time.LocalDate;
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

public class EditConnectionController {

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private final ControlledVocab vocab = ControlledVocab.getControlledVocab(); 
	private MainApp mainApp;
	public ArrayList<Person> peopleList = DS.getPeopleArray();
	
	@FXML
	private Button submit, home, add, search, remove, clear, toViewConnections, toConnectionInfo;
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
	private Connection currentConnection;
	private final ArrayList<Person> selectedRecipients = new ArrayList<>();

	public EditConnectionController() {
	}

	@FXML
	private void initialize() {
		currentConnection = SelectedInformationTracker.getSelectedConnection();
		recipientList.setItems(FXCollections.observableArrayList(nameList()));
		recipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		selectedRecipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		initiator.setItems(FXCollections.observableArrayList(nameList()));
		typeInput.setItems(vocab.getInteractionTypeOptions());
		addRecipients();
		removeRecipients();
		searchRecipients();
		clearRecipients();

		if (currentConnection.getSender() != null) {
			initiator.setValue(currentConnection.getSender().getName());
		}
		selectedRecipientList.setItems(FXCollections.observableArrayList(currentConnection.getReceiverNameList()));
		typeInput.setValue(currentConnection.getInteractionType());
		locationInput.setText(currentConnection.getLocation());
		citationInput.setText(currentConnection.getCitation());
		notes.setText(currentConnection.getNotes());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate date = LocalDate.parse(currentConnection.getDate(), dtf);
		dateInput.setValue(date);

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
	
	public void removeRecipients() {
		remove.setOnAction((event) -> {
			ObservableList<String> selectedRecipientsTemp = selectedRecipientList.getSelectionModel().getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				selectedRecipientList.getItems().remove(selectedRecipientsTemp.get(i));
			}
		});
	}
	
	public void clearRecipients() {
		clear.setOnAction(
				e -> recipientList.setItems(FXCollections.observableArrayList(nameList())));
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
		search.setOnAction((event) -> {
			String searchText = searchInput.getText();
			PersonQuery filter = new PersonContainsQuery(searchText, "Name");
			observableSet.clear();
			ArrayList<Person> array = DS.getPeopleArray();
			for (int i = 0; i < array.size(); i++){
				Person temp = array.get(i);
				if (filter.accepts(temp)){
					observableSet.add(temp.getName());
				}
			}
			recipientList.setItems(FXCollections.observableArrayList(observableSet));
		});
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
			String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			if (currentConnection.isGroupConnection()) {
				currentConnection.editConnection(selectedRecipients, date, typeInput.getValue(), location,
						citationInput.getText(), notes.getText());
			} else {
				String initiatorPerson = initiator.getValue();
				Person sender = DS.getPersonObject(initiatorPerson);
				currentConnection.editConnection(sender, selectedRecipients, date, typeInput.getValue(), location,
						citationInput.getText(), notes.getText());
			}
			DS.saveConnections();
			stage = (Stage) this.submit.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else if (event.getSource() == home) {
			stage = (Stage) this.home.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else if (event.getSource() == toViewConnections) {
			stage = (Stage) this.toViewConnections.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ViewConnections.fxml"));
		} else {
			stage = (Stage) this.toConnectionInfo.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ConnectionInfo.fxml"));
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