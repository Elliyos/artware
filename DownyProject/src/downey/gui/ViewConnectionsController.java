package downey.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import downey.main.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewConnectionsController {

	private final DataStorage DS = DataStorage.getMainDataStorage();
	private Person selectedPerson;
	private String selectedPeople;
	private boolean filterCheck = false;
	private ArrayList<Connection> filteredConnections;
	private ArrayList<Connection> connectionList = DS.getConnectionArray();
	private ObservableList<String> filteredList = FXCollections.observableArrayList();

	@FXML
	private ChoiceBox<String> filter;
	@FXML
	private ObservableList<String> observableConnectionList = FXCollections.observableArrayList();
	@FXML
	ListView<String> list = new ListView<String>();
	@FXML
	private Button goBack, viewButton, searchButton, clear, exportFilteredData, exportPalladio;
	@FXML
	private TextField target;


	public ViewConnectionsController() {
	}

	@FXML
	private void initialize() throws IOException {
		list.setItems(getConnectionList());
		filter.setItems(FXCollections.observableArrayList("Sender", "Receivers", "Date", "Location", "Citation",
				"Interaction Type", "Notes"));
		filter.setValue("Sender");
		filterAction();
		exportFilteredData.setOnAction((event) -> {
			exportToGephi();
		});
		exportPalladio.setOnAction((event) -> {
			exportToPalladio();
		});
		clearList();
	}

	/**
	 * Returns an observable list of existing, archived list of connections.
	 * @return
	 */
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

	/**
	 * Chooses a file name to export the archived information.
	 * @return
	 */
	public File getChosenFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a file name PREFIX for exporting:");
		File file = fileChooser.showSaveDialog(null);
		return file;
	}

	/**
	 * Handles exporting to Gephi.
	 */
	public void exportToGephi() {
		File file = getChosenFile();

		if (file != null) {
			Exporter gephiEx = new GephiExporter(filteredConnections, DS.getFilteredPeople(filteredConnections));
			try {
				gephiEx.export(file.getPath());
			} catch (IOException e) {
				// TODO: alert the user if error happens
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handles exporting to Palladio.
	 */
	public void exportToPalladio() {
		File file = getChosenFile();

		if (file != null) {
			Exporter palladioEx = new PalladioExporter(filteredConnections);
			try {
				palladioEx.export(file.getPath());
			} catch (IOException e) {
				// TODO: alert the user if error happens
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns a filtered list of names based on a search input by the user.
	 * @param query
	 * @return
	 */
	public ObservableList<String> filteredNameList(ConnectionQuery query) {
		filteredConnections = new ArrayList<Connection>();
		for (int i = 0; i <= connectionList.size() - 1; i++) {
			if (query.accepts(connectionList.get(i))) {
				Connection selectedConnection = connectionList.get(i);
				filteredConnections.add(connectionList.get(i));
				filteredList.addAll(Arrays.asList(
						selectedConnection.getSender().getName() + " : " + selectedConnection.getReceiverNameList()));
			}
		}
		return filteredList;
	}

	/**
	 * Creates a filtered list based on the search input given by the user. Maps to the search button.
	 */
	private void filterAction() {
		searchButton.setOnAction((e) -> {
			filteredList.clear();
			ConnectionQuery containsFilter = new ConnectionContainsQuery(target.getText(), filter.getValue());
			list.setItems(filteredNameList(containsFilter));
			filterCheck = true;
		});
	}

	/**
	 * Clears the list of any searches / filters.
	 */
	private void clearList() {
		clear.setOnAction(e -> {
			observableConnectionList.clear();
			list.setItems(getConnectionList());
			filterCheck = false;
		});
	}

	@FXML
	private void backAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) goBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		stage.setScene(new Scene(root));
		stage.setResizable(false);
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
			if (!filterCheck) {
				SelectedInformationTracker.storeSelectedConnection(DS.getConnectionArray().get(list.getSelectionModel().getSelectedIndex()));
			} else {
				SelectedInformationTracker.storeSelectedConnection(filteredConnections.get(list.getSelectionModel().getSelectedIndex()));
			}
			Stage stage = (Stage) viewButton.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("ConnectionInfo.fxml"));
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
		}
	}
}
