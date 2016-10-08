package downey.gui;

import java.io.IOException;
import java.util.ArrayList;

import downey.main.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;


public class EditInfoController {

	@FXML
	private TextField nameArea;
	
	@FXML
	private TextField occupationArea;
	
	@FXML
	private TextField cultureArea;
	
	@FXML
	private TextArea bioArea;
	
	@FXML
	private ChoiceBox<String> genderInput;
	
	@FXML
	private Button submit;
	
	@FXML
	private Button goBack;
	
	private DataStorage DS = DataStorage.getMainDataStorage();
	private MainApp mainApp;
	private ArrayList<Person> peopleList = DS.getPeopleArray();
	private Person currentPerson;
	
	public EditInfoController(){
	}
	
	@FXML
	private void initialize() throws IOException{
		currentPerson = DS.getPersonObject(DS.getSelectedName());
		occupationArea.setPromptText(currentPerson.getOccupation());
		cultureArea.setPromptText(currentPerson.getCulture());
		bioArea.setPromptText(currentPerson.getBio());
		genderInput.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
	}
	
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage; 
        Parent root;
        if (event.getSource() == submit){       
           currentPerson.editPerson(nameArea.getText(), cultureArea.getText(), occupationArea.getText(), genderInput.getValue(), bioArea.getText());
           DS.savePeople("people");
        	stage=(Stage) submit.getScene().getWindow();
           root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
         } else {
        	 stage=(Stage) goBack.getScene().getWindow();
        	 root = FXMLLoader.load(getClass().getResource("PersonInfo.fxml"));
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
