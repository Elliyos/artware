package downey.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import downey.main.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class FindPersonController {

	private MainApp mainApp;
	private final DataStorage DS = DataStorage.getMainDataStorage();
	private final ArrayList<Person> peopleList = DS.getPeopleArray();
	private Person selectedPerson;

	private final ObservableSet<String> observablePeopleSet = FXCollections.observableSet();
	private ObservableSet<String> filteredSet = FXCollections.observableSet();
	@FXML
	ListView<String> list = new ListView<String>();
	@FXML
	ListView<String> filterList = new ListView<String>();
	@FXML
	private Button goBack, viewButton, searchButton, clear, gephiExportButton, palladioExportButton;
	@FXML
	private ChoiceBox<String> filter;
	@FXML
	private TextField target;
	private ArrayList<Person> filteredPeople = new ArrayList<>();

	public FindPersonController() {
	}

	@FXML
	private void initialize() throws IOException {
		list.setItems(FXCollections.observableArrayList(getNameList()));
		filter.setItems(FXCollections.observableArrayList("Name", "Nickname", "Occupation", "Culture", "Gender"));
		filter.setValue("Name");
		filterAction();
		gephiExportButton.setOnAction((event)-> { exportToGephi(); });
		palladioExportButton.setOnAction((event)-> { exportToPalladio(); });
		clearList();
	}

	public ObservableSet<String> getNameList() {
		for (int i = 0; i <= peopleList.size() - 1; i++) {
			selectedPerson = peopleList.get(i);
			String name = selectedPerson.getName();
			observablePeopleSet.addAll(Arrays.asList(name));
		}
		return observablePeopleSet;
	}

	public ObservableSet<String> filteredNameList(PersonQuery query) {
		for (int i = 0; i <= peopleList.size() - 1; i++) { 
			if (query.accepts(peopleList.get(i))) {
				selectedPerson = peopleList.get(i);
				filteredPeople.add(peopleList.get(i));
				String name = selectedPerson.getName();
				filteredSet.addAll(Arrays.asList(name));
			}
		}
		return filteredSet;
	}

	private void filterAction() {
		searchButton.setOnAction((e) -> {
			filteredSet.clear();
			PersonQuery containsFilter = new PersonContainsQuery(target.getText(), filter.getValue());
			list.setItems(FXCollections.observableArrayList(filteredNameList(containsFilter)));
		});
	}public File getChosenFile(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a file name PREFIX for exporting:");
        File file = fileChooser.showSaveDialog(null);
        return file;
	}
	public void exportToGephi() {
		File file = getChosenFile();
		
		if (file != null) {
			Exporter gephiEx = new GephiExporter(DS.getFilteredConnections(filteredPeople), filteredPeople);
			try {
			gephiEx.export(file.getPath());
			} catch (IOException e) {
				//TODO: alert the user if error happens
				e.printStackTrace();
			}
	}
	}
	public void exportToPalladio(){
		File file = getChosenFile();
		
		if (file != null) {
			Exporter palladioEx = new PalladioExporter(DS.getFilteredConnections(filteredPeople));
			try {
			palladioEx.export(file.getPath());
			} catch (IOException e) {
				//TODO: alert the user if error happens
				e.printStackTrace();
			}
	}
	}
	
	private void clearList(){
		clear.setOnAction(e -> {
			list.setItems(FXCollections.observableArrayList(getNameList()));
		});
	}

	@FXML
	private void viewAction() throws IOException {
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
	private void backAction() throws IOException {
		Stage stage = (Stage) goBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
	}
	
//	private void exportAction() {
//		export.setOnAction((event) -> {
//			FileChooser fileChooser = new FileChooser();
//			fileChooser.setTitle("Open Resource File");
//			
//		});
//	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}