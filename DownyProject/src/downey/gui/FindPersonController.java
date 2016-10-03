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
import javafx.scene.control.ChoiceBox;

public class FindPersonController {

	private MainApp mainApp;
	private DataStorage DS = new DataStorage();
	private ArrayList<Person> peopleList = DS.getPeopleArray();
	private String names = nameList(peopleList);
	
	@FXML
	ListView<String> list = new ListView<String>();
	@FXML
	private Button goBack;
	@FXML
	private Button viewButton;
	@FXML
	ObservableList<String> people = FXCollections.observableArrayList (names);
	
    public FindPersonController() {
    }
    
    @FXML
    private void initialize() throws IOException {
    	list.setItems(people);
    }
    
    public String nameList(ArrayList<Person> peopleList){
    	String names = "";
    	for (int i = 0; i <= peopleList.size()-1; i++){
    		Person currentPerson = peopleList.get(i);
    		names += currentPerson.getName() + ", ";
    	}
    	return names;
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage; 
        Parent root;
        if (event.getSource() == viewButton){
           //get reference to the button's stage         
           stage=(Stage) viewButton.getScene().getWindow();
           //load up OTHER FXML document
           root = FXMLLoader.load(getClass().getResource("PersonInfo.fxml"));
         } else {
        	 stage=(Stage) goBack.getScene().getWindow();
        	 root = FXMLLoader.load(getClass().getResource("ProfileOptions.fxml"));
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
