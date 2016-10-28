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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

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
	private Button goBack, viewButton;

	public FindPersonController() {
	}

	@FXML
	private void initialize() throws IOException {
		list.setItems(FXCollections.observableArrayList(nameList(peopleList)));
	}

	public ObservableSet<String> nameList(ArrayList<Person> peopleList) {
		String name;
		for (int i = 0; i <= peopleList.size() - 1; i++) {
			selectedPerson = peopleList.get(i);
			name = selectedPerson.getName();
			observableSet.addAll(Arrays.asList(name));
		}
		return observableSet;
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
		Parent root = FXMLLoader.load(getClass().getResource("ProfileOptions.fxml"));	
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
