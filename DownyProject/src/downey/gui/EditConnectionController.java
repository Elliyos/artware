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
	private Connection currentConnection;
	private ArrayList<Person> selectedRecipients = new ArrayList<>();

	public EditConnectionController() {
	}

	@FXML
	private void initialize() {
		currentConnection = SelectedInformationTracker.getSelectedConnection();
		recipientList.setItems(FXCollections.observableArrayList(nameList(DS.getPeopleArray())));
		recipientList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		selectedRecipientList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		initiator.setItems(FXCollections.observableArrayList(nameList(DS.getPeopleArray())));
		typeInput.setItems(FXCollections.observableArrayList("Letter", "Email", "Meeting", "Party"));
		search.setOnAction((event) -> {
			String searchText = searchInput.getText();
			observableSet.clear();
			Person temp = DS.getPersonObject(searchText);
			observableSet.add(temp.getName());
			recipientList.setItems(FXCollections.observableArrayList(observableSet));
		});
		add.setOnAction((event) -> {
			observableSet.clear();
			ObservableList<String> selectedRecipientsTemp = recipientList.getSelectionModel().getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				selectedRecipients.add(DS.getPersonObject(selectedRecipientsTemp.get(i)));
			}
			selectedRecipientList.setItems(FXCollections.observableArrayList(nameList(selectedRecipients)));
		});
		remove.setOnAction((event) -> {
			selectedRecipients.clear();
			ObservableList<String> selectedRecipientsTemp = selectedRecipientList.getSelectionModel()
					.getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				selectedRecipientList.getItems().remove(i);
			}

		});
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
			String location = locationInput.getText();
			String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			if (location.equals("")) location = "Unknown";
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
		} else {
			stage = (Stage) this.goBack.getScene().getWindow();
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