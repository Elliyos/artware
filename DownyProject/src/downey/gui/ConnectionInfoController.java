package downey.gui;

import java.io.IOException;
import downey.main.Connection;
import downey.main.SelectedInformationTracker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ConnectionInfoController {

	@FXML
	private Button home, editButton, toViewConnections;
	@FXML
	private Label dateLabel, typeLabel, locationLabel, citationLabel, notesLabel, sender, recipients;
	@FXML
	private TextArea notesArea;
	private Connection currentConnection;

	public ConnectionInfoController() {
	}

	@FXML
	private void initialize() throws IOException {
		currentConnection = SelectedInformationTracker.getSelectedConnection();

		if (currentConnection.getSender() != null) {
			sender.setText(currentConnection.getSender().getName());
		}
		recipients.setText(currentConnection.getReceiverNameList().toString());
		dateLabel.setText(currentConnection.getDate());
		typeLabel.setText(currentConnection.getInteractionType());
		locationLabel.setText(currentConnection.getLocation());
		citationLabel.setText(currentConnection.getCitation());
		notesArea.setText(currentConnection.getNotes());
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == home) {
			stage = (Stage) home.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else if (event.getSource() == editButton) {
			stage = (Stage) editButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("EditConnection.fxml"));
		} else {
			stage = (Stage) toViewConnections.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ViewConnections.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
