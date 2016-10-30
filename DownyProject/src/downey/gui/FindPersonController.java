package downey.gui;

import java.io.IOException;
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
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class FindPersonController {

	private MainApp mainApp;
	private final DataStorage DS = DataStorage.getMainDataStorage();
	private final ArrayList<Person> peopleList = DS.getPeopleArray();
	private Person selectedPerson;

	@FXML
	private final ObservableSet<String> observableSet = FXCollections.observableSet();
	@FXML
	ObservableList<String> people = FXCollections.observableArrayList();
	@FXML
	ListView<String> list = new ListView<String>(people);
	@FXML
	ListView<String> filterList = new ListView<String>();
	@FXML
	private Button goBack, viewButton, searchButton;
	@FXML
	private ChoiceBox<String> filter;
	@FXML
	private TextField target;

	public FindPersonController() {
	}

	@FXML
	private void initialize() throws IOException {
		list.setItems(FXCollections.observableArrayList(nameList(peopleList)));
		filter.setItems(FXCollections.observableArrayList("Name", "Occupation", "Culture", "Gender"));
	}

	private void addToSet(int index) {
		selectedPerson = peopleList.get(index);
		String name = selectedPerson.getName();
		observableSet.addAll(Arrays.asList(name));
	}

	public ObservableSet<String> nameList(ArrayList<Person> peopleList) {
		for (int i = 0; i <= peopleList.size() - 1; i++)
			addToSet(i);
		return observableSet;
	}

	public ObservableSet<String> filteredNameList(ArrayList<Person> peopleList, PersonQuery query) {
		for (int i = 0; i <= peopleList.size() - 1; i++) 
			if (query.accepts(peopleList.get(i)))
				addToSet(i);
		return observableSet;
	}

	@FXML
	private void filterAction(ActionEvent event) {
		PersonQuery containsFilter = new PersonContainsQuery(target, filter.getSelectionModel().getSelectedItem());
		list.setItems(FXCollections.observableArrayList(filteredNameList(peopleList, containsFilter)));

	}

	@FXML
	private void viewAction(ActionEvent event) throws IOException {
		String selectedName = list.getSelectionModel().getSelectedItem();
		if (selectedName == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Select Person");
			alert.setHeaderText(null);
			alert.setContentText("Select a Person to view!");
			alert.showAndWait();
		} else {
			SelectedInformationTracker.storeSelectedName(selectedName);
			Stage stage = (Stage) viewButton.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("PersonInfo.fxml"));
			stage.setScene(new Scene(root));
			stage.show();
		}
	}

	@FXML
	private void backAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) goBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
