package downey.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConnectionOptionsController {

	@FXML
	private Button addButton;
	@FXML
	private Button viewButton;
	@FXML
	private Button goBack;
	private MainApp mainApp;

	public ConnectionOptionsController() {
	}

	@FXML
	private void initialize() {
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if (event.getSource() == this.addButton) {
			stage = (Stage) this.addButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("AddConnection.fxml"));
		} else if (event.getSource() == this.goBack) {
			stage = (Stage) this.goBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		} else {
			stage = (Stage) this.viewButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("ViewConnections.fxml"));
		}
		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
