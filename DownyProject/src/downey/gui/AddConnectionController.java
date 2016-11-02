package downey.gui;

import java.io.IOException;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class AddConnectionController {

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private final ControlledVocab vocab = ControlledVocab.getControlledVocab(); 
	private MainApp mainApp;
	public ArrayList<Person> peopleList = DS.getPeopleArray();
	private Person selectedPerson;
	private final ArrayList<Person> selectedRecipients = new ArrayList<>();
	private final ObservableSet<String> observableSet = FXCollections.observableSet();
	private ObservableSet<String> filteredSet = FXCollections.observableSet();
	
	@FXML
	private Button submit, goBack, add, search, remove, clear, editChoices, locationVocabAdd, locationVocabRemove, typeVocabAdd, typeVocabRemove;
	@FXML
	private ChoiceBox<String> initiator, typeInput, locationInput;
	@FXML
	private DatePicker dateInput; 
	@FXML
	private TextField citationInput, searchInput;
	@FXML
	private TextArea notes;
	@FXML
	ListView<String> recipientList = new ListView<String>();
	@FXML
	ListView<String> selectedRecipientList = new ListView<>();
	
	public AddConnectionController() {}

	@FXML
	private void initialize() {
		recipientList.setItems(FXCollections.observableArrayList(getNameList()));
		recipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		selectedRecipientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		initiator.setItems(FXCollections.observableArrayList(getNameList()));
		typeInput.setItems(vocab.getInteractionTypeOptions());
		locationInput.setItems(vocab.getLocationOptions());
		addRecipients();
		removeRecipients();
		searchRecipients();
		clearRecipients();
	}

	/**
	 * Returns an observable set of names to be used in our list.
	 * @return
	 */
	public ObservableSet<String> getNameList() {
		for (int i = 0; i <= peopleList.size() - 1; i++) {
			selectedPerson = peopleList.get(i);
			observableSet.addAll(Arrays.asList(selectedPerson.getName()));
		}
		return observableSet;
	}
	
	/**
	 * Filters a list of names; to be used with search functionality.
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
	
	/**
	 * Sets an action to the add button and adds recipients to the recipient list.
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
	 * Adds an action to the search button to populate a list with the returned recipients.
	 */
	public void searchRecipients() {
		search.setOnAction((e) -> {
			filteredSet.clear();
			PersonQuery containsFilter = new PersonContainsQuery(searchInput.getText(), "Name");
			recipientList.setItems(FXCollections.observableArrayList(filteredNameList(containsFilter)));
		});
	}
	
	/**
	 * Adds an action to the remove button to remove a selected recipient from the list of selected recipients.
	 */
	public void removeRecipients() {
		remove.setOnAction((event) -> {
			ObservableList<String> selectedRecipientsTemp = selectedRecipientList.getSelectionModel().getSelectedItems();
			for (int i = 0; i < selectedRecipientsTemp.size(); i++) {
				selectedRecipientList.getItems().remove(selectedRecipientsTemp.get(i));
			}
		});
	}
	
	/**
	 * Clears the list created by search.
	 */
	public void clearRecipients() {
		clear.setOnAction(e -> recipientList.setItems(FXCollections.observableArrayList(getNameList())));
	}

	public void createConnection() throws IOException{
		for (int i = 0; i < selectedRecipientList.getItems().size(); i++) {
			String name = selectedRecipientList.getItems().get(i);
			selectedRecipients.add(DS.getPersonObject(name));
		}
		String location = locationInput.getValue();
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
			if (result.isPresent()){
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
			if (result.isPresent()){
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