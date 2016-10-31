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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ViewConnectionsController {


	private MainApp mainApp;
	private final DataStorage DS = DataStorage.getMainDataStorage();
	private Person selectedPerson;
	private String selectedPeople;

	@FXML
	private ChoiceBox<String> filter;
	@FXML
	private final ObservableSet<String> observableConnectionList = FXCollections.observableSet();
	@FXML
	ObservableList<String> connection = FXCollections.observableArrayList();
	@FXML
	ListView<String> list = new ListView<String>();
	@FXML
	private Button goBack, viewButton, searchButton, clear;
	@FXML
	private ArrayList<Connection> connectionsList = DS.getConnectionArray();
	@FXML
	private ObservableSet<String> filteredSet = FXCollections.observableSet();
	@FXML
	private TextField target;

	public ViewConnectionsController() {
	}
		
	private ObservableSet<String> getConnectionList(ArrayList<Connection> connectionList) {
		for (int i = 0; i <= connectionList.size() - 1; i++) {
			if (connectionList.get(i).getSender() != null) {
				selectedPerson = connectionList.get(i).getSender(); 
				selectedPeople = connectionList.get(i).getReceiverNameList().toString();
				observableConnectionList.addAll(Arrays.asList(selectedPerson.getName() + ": " + selectedPeople));
			} else {
				selectedPeople = connectionList.get(i).getReceiverNameList().toString();
				observableConnectionList.addAll(Arrays.asList(" : " + selectedPeople));
			}
		}
		return observableConnectionList;
	}
	

	@FXML
	private void initialize() throws IOException {
		list.setItems(FXCollections.observableArrayList(getConnectionList(connectionsList)));
		filter.setItems(FXCollections.observableArrayList("Sender", "Receivers", "Date", "Location", "Citation", "Interaction Type", "Notes"));
		filter.setValue("Sender");
	}
	
	public ObservableSet<String> filteredNameList(ConnectionQuery query) {
		for (int i = 0; i <= connectionsList.size() - 1; i++) { 
			if (query.accepts(connectionsList.get(i))) {
				Connection selectedConnection = connectionsList.get(i);
				filteredSet.addAll(Arrays.asList(selectedConnection.toString()));
			}
		}
		return filteredSet;
	}
	
	@FXML
	private void filterAction(ActionEvent event) {
		searchButton.setOnAction((e) -> {
			filteredSet.clear();
			ConnectionQuery containsFilter = new ConnectionContainsQuery(target.getText(), filter.getValue());
			list.setItems(FXCollections.observableArrayList(filteredNameList(containsFilter)));
		});
	}
	
	@FXML
	private void clearList(ActionEvent event){
		clear.setOnAction(e -> {
			list.setItems(FXCollections.observableArrayList(getConnectionList(connectionsList)));
		});
	}


	@FXML
	private void backAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) goBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	@FXML
	private void viewAction(ActionEvent event) throws IOException {
		String selectedConnection = list.getSelectionModel().getSelectedItem();
		if (selectedConnection == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Select Connection");
			alert.setHeaderText(null);
			alert.setContentText("Select a Connection to view!");
			alert.showAndWait();
		} else {
			String[] names = selectedConnection.split(":");
			SelectedInformationTracker.storeSelectedName(names[0]);
			SelectedInformationTracker.storeSelectedNames(names[1]);
			SelectedInformationTracker.storeSelectedConnection(DS.getConnectionArray().get(list.getSelectionModel().getSelectedIndex()));
			Stage stage = (Stage) viewButton.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("ConnectionInfo.fxml"));
			stage.setScene(new Scene(root));
			stage.show();
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
