package downey.gui;

import java.io.IOException;
import downey.main.Connection;
import downey.main.DataStorage;
import downey.main.SelectedInformationTracker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ConnectionInfoController {
	private MainApp mainApp;
	private final DataStorage DS = DataStorage.getMainDataStorage();
	
	@FXML
	private Button goBack, editButton;
	@FXML
	private Label dateLabel, typeLabel, locationLabel, citationLabel, notesLabel, sender, recipients;
	@FXML
	private TextArea notesArea;
	private Connection currentConnection;
	

	public ConnectionInfoController() {
    }
    
    /**
	 * This method is currently bugged. Information has been place inside to replicate an actual use case. Needs to be implemented in sprint 2.
	 * @throws IOException
	 */
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
    private void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage; 
        Parent root;
        if (event.getSource() == goBack){       
           stage=(Stage) goBack.getScene().getWindow();
           root = FXMLLoader.load(getClass().getResource("ViewConnections.fxml"));
         } else {
        	 stage=(Stage) editButton.getScene().getWindow();
        	 root = FXMLLoader.load(getClass().getResource("EditConnection.fxml"));
         }
        //create a new scene with root and set the stage
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
       }
    

	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }
}
