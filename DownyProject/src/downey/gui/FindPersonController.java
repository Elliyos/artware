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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class FindPersonController {

	private MainApp mainApp;
	private DataStorage DS = DataStorage.getMainDataStorage();
	private ArrayList<Person> peopleList = DS.getPeopleArray();
	private Person selectedPerson;

	@FXML
	private ObservableSet<String> observableSet = FXCollections.observableSet();
	@FXML
	ObservableList<String> people = FXCollections.observableArrayList();
	@FXML
	ListView<String> list = new ListView<String>(people);
	@FXML
	private Button goBack;
	@FXML
	private Button viewButton;

	public FindPersonController() {
	}

	@FXML
	private void initialize() throws IOException {
		list.setItems(FXCollections.observableArrayList(nameList(peopleList)));
//		list.setItems(FXCollections.observableArrayList(peopleList.get(0).getName()));
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
		if (event.getSource() == viewButton) {
			String selectedName = list.getSelectionModel().getSelectedItem();
			SelectedInformationTracker.storeSelectedName(selectedName);
			stage = (Stage) viewButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("PersonInfo.fxml"));
			
		} else { // go back
			stage = (Stage) goBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ProfileOptions.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
