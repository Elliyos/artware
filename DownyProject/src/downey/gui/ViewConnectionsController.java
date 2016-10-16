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
	private ArrayList<Connection> connectionList = DS.getConnectionArray();
	private String selectedName;
	private Person selectedPerson;
	private String selectedPeople;

	@FXML
	private ObservableSet<String> observableSet = FXCollections.observableSet();
	//private ObservableSet<Connection> observableSet = FXCollections.observableSet();
	@FXML
	ObservableList<String> connection = FXCollections.observableArrayList();
	//ObservableList<Connection> connection = FXCollections.observableArrayList();
	@FXML
	ListView<String> list = new ListView<String>();
	//ListView<Connection> list = new ListView<Connection>();
	@FXML
	private Button goBack;
	@FXML
	private Button viewButton;

	public ViewConnectionsController() {
	}
	// dont change this method
	public ObservableSet<String> nameList(ArrayList<Connection> peopleList) {
		String name = "";
		for (int i = 0; i <= peopleList.size() - 1; i++) {
			selectedPerson = peopleList.get(i).getSender(); 
			selectedPeople = peopleList.get(i).getReceiverNames();
			name = selectedPerson.getName();
			observableSet.addAll(Arrays.asList(name + ": " + selectedPeople));
		}
		return observableSet;
	}

	@FXML
	private void initialize() throws IOException {
		//list.setItems(FXCollections.observableArrayList(observableSet));// REAL, WORK WITH THIS
		list.setItems(FXCollections.observableArrayList(nameList(DS.getConnectionArray())));
	}
	public void storeData(){
		String[] names = list.getSelectionModel().getSelectedItem().split(":");
		DS.storeSelectedName(names[0]);
		DS.storeSelectedNames(names[1]);
		System.out.println(list.getSelectionModel().getSelectedIndex());
		DS.storeSelectedConnection(connectionList.get(list.getSelectionModel().getSelectedIndex()));
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
