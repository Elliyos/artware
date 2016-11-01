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
	private ObservableList<String> observableConnectionList = FXCollections.observableArrayList();
	@FXML
	ListView<String> list = new ListView<String>();
	@FXML
	private Button goBack, viewButton, searchButton, clear;
	private ArrayList<Connection> connectionList = DS.getConnectionArray();
	private ObservableList<String> filteredList = FXCollections.observableArrayList();
	@FXML
	private TextField target;

	public ViewConnectionsController() {
	}
	
	@FXML
	private void initialize() throws IOException {
		list.setItems(getConnectionList());
		filter.setItems(FXCollections.observableArrayList("Sender", "Receivers", "Date", "Location", "Citation", "Interaction Type", "Notes"));
		filter.setValue("Sender");
		filterAction();
		clearList();
	}
		
	private ObservableList<String> getConnectionList() {
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
	
	public ObservableList<String> filteredNameList(ConnectionQuery query) {
		for (int i = 0; i <= connectionList.size() - 1; i++) { 
			if (query.accepts(connectionList.get(i))) {
				Connection selectedConnection = connectionList.get(i);
				filteredList.addAll(Arrays.asList(selectedConnection.getSender().getName() + " : " + selectedConnection.getReceiverNameList()));
			}
		}
		return filteredList;
	}
	
	private void filterAction() {
		searchButton.setOnAction((e) -> {
			filteredList.clear();
			ConnectionQuery containsFilter = new ConnectionContainsQuery(target.getText(), filter.getValue());
			list.setItems(filteredNameList(containsFilter));
		});
	}
	
	private void clearList(){
		clear.setOnAction(e -> {
			observableConnectionList.clear();
			list.setItems(getConnectionList());
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
		System.out.println();
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
