package downey.gui;

import java.io.IOException;
import java.util.ArrayList;

import downey.main.DataStorage;
import downey.main.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConnectionInfoController {
	private MainApp mainApp;
	private DataStorage DS = new DataStorage();
	private ArrayList<Person> peopleList = DS.getPeopleArray();
	
	@FXML
	private Button goBack;
	@FXML
	private Button editButton;
	@FXML
	private Label dateLabel;
	@FXML
	private Label typeLabel;
	@FXML
	private Label locationLabel;
	@FXML
	private Label citationLabel;
	@FXML
	private Label notesLabel;
	@FXML
	private Label sender;
	@FXML
	private Label recipients;
	

	public ConnectionInfoController() {

    }
    
    
	/**
	 * This method is currently bugged. Information has been place inside to replicate an actual use case. Needs to be implemented in sprint 2.
	 * @throws IOException
	 */
	@FXML
    private void initialize() throws IOException {
		sender.setText("Kyle");
    	recipients.setText("Stonedahl, Lyli");
    	dateLabel.setText("11/17/2015");
    	typeLabel.setText("House Visit");
    	locationLabel.setText("Silvis, IL, USA");
    	citationLabel.setText("Encyclopedia Vol. 1 Ed 3");
    	notesLabel.setText("\"You're a wizard, Kyle,\" Stonedahl said.");
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
