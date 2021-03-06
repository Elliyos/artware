package downey.gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditConnectionController {

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private final ControlledVocab vocab = ControlledVocab.getControlledVocab();
	public ArrayList<Person> peopleList = DS.getPeopleArray();
	private final ObservableSet<String> observableSet = FXCollections.observableSet();
	private ObservableSet<String> filteredSet = FXCollections.observableSet();
	private boolean isGroupConnection;

	@FXML
	private Button submit, home, add, search, remove, clear, toViewConnections, toConnectionInfo, editChoices,
			locationVocabAdd, locationVocabRemove, typeVocabAdd, typeVocabRemove;
	@FXML
	private ChoiceBox<String> initiator, typeInput, locationInput;
	@FXML
	private DatePicker dateInput;
	@FXML
	private TextField citationInput, searchInput;
	@FXML
	private TextArea notes;
	@FXML
	private CheckBox groupConnection;
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
		locationInput.setItems(vocab.getLocationOptions());
		groupConnection.setSelected(currentConnection.isGroupConnection());
		setBooleanValues();
		addRecipients();
		removeRecipients();
		searchRecipients();
		clearRecipients();
		locationVocabAdd();
		locationVocabRemove();
		typeVocabAdd();
		typeVocabRemove();
		if (currentConnection.getSender() != null) {
			initiator.setValue(currentConnection.getSender().getName());
		}
		selectedRecipientList.setItems(FXCollections.observableArrayList(currentConnection.getReceiverNameList()));
		typeInput.setValue(currentConnection.getInteractionType());
		locationInput.setValue(currentConnection.getLocation());
		citationInput.setText(currentConnection.getCitation());
		notes.setText(currentConnection.getNotes());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate date = LocalDate.parse(currentConnection.getDate(), dtf);
		dateInput.setValue(date);
		groupConnection.setOnAction((event) -> {
			setBooleanValues();
		});
	}
	
	private void setBooleanValues() {
		if (groupConnection.isSelected()) {
			initiator.setDisable(true);
			initiator.setValue(null);
			isGroupConnection = true;
		} else {
			initiator.setDisable(false);
			isGroupConnection = false;
		}
	}

	/**
	 * Returns a list of names to be used in our list.
	 * 
	 * @return
	 */
	public ObservableSet<String> nameList() {
		for (int i = 0; i <= peopleList.size() - 1; i++) {
			selectedPerson = peopleList.get(i);
			observableSet.addAll(Arrays.asList(selectedPerson.getName()));
		}
		return observableSet;
	}

	/**
	 * Takes a search input and filters the name list based on it.
	 * 
	 * @param query
	 * @return
	 */
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

	/**
	 * Removes recipients from the list of included recipients.
	 */
	public void removeRecipients() {
		remove.setOnAction((event) -> {
			ObservableList<String> selectedRecipientsTemp = selectedRecipientList.getSelectionModel()
					.getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				selectedRecipientList.getItems().remove(selectedRecipientsTemp.get(i));
			}
		});
	}

	/**
	 * Clears the search in the list of recipients.
	 */
	public void clearRecipients() {
		clear.setOnAction(e -> recipientList.setItems(FXCollections.observableArrayList(nameList())));
	}

	/**
	 * Adds recipients to the connection.
	 */
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

	/**
	 * Takes an input from the user and populates the list of recipients with
	 * returned recipients.
	 */
	public void searchRecipients() {
		search.setOnAction((event) -> {
			String searchText = searchInput.getText();
			PersonQuery filter = new PersonContainsQuery(searchText, "Name");
			observableSet.clear();
			ArrayList<Person> array = DS.getPeopleArray();
			for (int i = 0; i < array.size(); i++) {
				Person temp = array.get(i);
				if (filter.accepts(temp)) {
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
			editConnection();
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
		stage.setResizable(false);
		stage.show();
	}

	private void editConnection() throws IOException {
		for (int i = 0; i < selectedRecipientList.getItems().size(); i++) {
			String name = selectedRecipientList.getItems().get(i);
			selectedRecipients.add(DS.getPersonObject(name));
		}
		String location = locationInput.getValue();
		if (location == null)
			location = "Unknown";
		String date = "";
		if (dateInput.getValue() != null)
			date = dateInput.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		String initiatorName = initiator.getValue();
		if (!isGroupConnection && initiatorName != null) {
			Person sender = DS.getPersonObject(initiatorName);
			currentConnection.editConnection(sender, selectedRecipients, date, typeInput.getValue(), location,
					citationInput.getText(), notes.getText());

		} else if ((!isGroupConnection && initiatorName == null) || (dateInput.getValue() == null)
				|| (selectedRecipients == null) || (typeInput.getValue() == null)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Input");
			alert.setHeaderText(null);
			alert.setContentText("Could Not Add Connection");
			alert.showAndWait();
		} else {
			DS.removeConnection(currentConnection);
			DS.addGroupConnection(selectedRecipients, date, typeInput.getValue(), location, citationInput.getText(),
					notes.getText());
		}
		DS.saveConnections();
	}

	@FXML
	private void locationVocabAdd() {
		locationVocabAdd.setOnAction(e -> {
			TextInputDialog input = new TextInputDialog();
			input.setTitle("Add Location");
			input.setHeaderText(null);
			input.setContentText("Enter a new location:");
			Optional<String> addedChoice = input.showAndWait();
			if (addedChoice.isPresent() && !addedChoice.get().equals("")) {
				vocab.addLocationOption(addedChoice.get());
			}
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	@FXML
	private void locationVocabRemove() {
		locationVocabRemove.setOnAction(e -> {
			List<String> choices = vocab.getLocationOptions();

			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Remove a Location");
			dialog.setHeaderText(null);
			dialog.setContentText("Choose a location to remove:");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				vocab.removeLocationOption(result.get());
			}

			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	@FXML
	private void typeVocabAdd() {
		typeVocabAdd.setOnAction(e -> {
			TextInputDialog input = new TextInputDialog();
			input.setTitle("Add Interaction Type");
			input.setHeaderText(null);
			input.setContentText("Enter a new interaction type:");
			Optional<String> addedChoice = input.showAndWait();
			if (addedChoice.isPresent() && !addedChoice.get().equals("")) {
				vocab.addInteractionTypeOption(addedChoice.get());
			}
			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	@FXML
	private void typeVocabRemove() {
		typeVocabRemove.setOnAction(e -> {
			List<String> choices = vocab.getInteractionTypeOptions();

			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Remove an Interaction Type");
			dialog.setHeaderText(null);
			dialog.setContentText("Choose an interaction type to remove:");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				vocab.removeInteractionTypeOption(result.get());
			}

			try {
				vocab.saveControlledVocab();
				vocab.loadControlledVocab();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
}