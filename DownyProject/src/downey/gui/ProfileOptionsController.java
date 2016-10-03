package downey.gui;

import java.io.IOException;
import java.util.ArrayList;

import downey.main.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ProfileOptionsController {

	@FXML
	private Button addButton;
	@FXML
	private Button viewButton;
	@FXML
	private Button editButton;
	@FXML
	private Button goBack;

	
	private MainApp mainApp;
	
	public ProfileOptionsController(){
	}
	
	@FXML
	private void initialize(){
	}
	
	 @FXML
	    private void handleButtonAction(ActionEvent event) throws IOException{
	        Stage stage; 
	        Parent root;
	        if (event.getSource() == addButton){
	           //get reference to the button's stage         
	           stage=(Stage) addButton.getScene().getWindow();
	           //load up OTHER FXML document
	           root = FXMLLoader.load(getClass().getResource("AddInfo.fxml"));
	         } else if (event.getSource() == viewButton){
	        	 stage=(Stage) viewButton.getScene().getWindow();
	        	 root = FXMLLoader.load(getClass().getResource("FindPerson.fxml"));
	         } else if (event.getSource() == editButton) {
	        	 stage=(Stage) editButton.getScene().getWindow();
	        	 root = FXMLLoader.load(getClass().getResource("EditFindPerson.fxml"));
	         } else {
	        	 stage=(Stage) goBack.getScene().getWindow();
	        	 root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
	         }
	        //create a new scene with root and set the stage
	         Scene scene = new Scene(root);
	         stage.setScene(scene);
	         stage.show();
	       }
	 
	 @FXML
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }

}
