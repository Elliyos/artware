package downey.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import downey.main.Connection;
import downey.main.DataStorage;
import downey.main.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ViewConnectionsController {


	private MainApp mainApp;
	private DataStorage DS = DataStorage.getMainDataStorage();
	private Person selectedPerson;
	private String selectedPeople;

	@FXML
	private ObservableList<String> observableConnectionList = FXCollections.observableArrayList();
	@FXML
	ObservableList<String> connection = FXCollections.observableArrayList();
	@FXML
	ListView<String> list = new ListView<String>();
	@FXML
	private Button goBack, viewButton;

	public ViewConnectionsController() {
	}
	
	private ObservableList<String> getConnectionList(ArrayList<Connection> connectionList) {
		for (int i = 0; i <= connectionList.size() - 1; i++) {
			selectedPerson = connectionList.get(i).getSender(); 
			selectedPeople = connectionList.get(i).getReceiverNames();
			observableConnectionList.addAll(Arrays.asList(selectedPerson.getName() + ": " + selectedPeople));
		}
		return observableConnectionList;
	}
	

	@FXML
	private void initialize() throws IOException {
		list.setItems(getConnectionList(DS.getConnectionArray()));
	}
	public void storeData(){
		String[] names = list.getSelectionModel().getSelectedItem().split(":");
		DS.storeSelectedName(names[0]);
		DS.storeSelectedNames(names[1]);
		DS.storeSelectedConnection(DS.getConnectionArray().get(list.getSelectionModel().getSelectedIndex()));
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == viewButton) {
			storeData();
			stage = (Stage) viewButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ConnectionInfo.fxml"));
		} else {
			stage = (Stage) goBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ConnectionOptions.fxml"));
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
