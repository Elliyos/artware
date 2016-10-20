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
	private ChoiceBox<String> occupationArea, cultureArea, genderInput;
	@FXML
	private TextArea bioArea;	
	@FXML
	private Button submit, goBack;
	
	private DataStorage DS = DataStorage.getMainDataStorage();
	private MainApp mainApp;
	private ArrayList<Person> peopleList = DS.getPeopleArray();
	private Person currentPerson;
	
	public EditInfoController(){}
	
	@FXML
	private void initialize() throws IOException{
		currentPerson = DS.getPersonObject(DS.getSelectedName());
		nameArea.setText(currentPerson.getName());
		occupationArea.setValue(currentPerson.getOccupation());
		cultureArea.setValue(currentPerson.getCulture());
		bioArea.setText(currentPerson.getBio());
		genderInput.setValue(currentPerson.getGender());
		occupationArea.setItems(FXCollections.observableArrayList("Sculptor", "Scholar", "Painter"));
		cultureArea.setItems(FXCollections.observableArrayList("American", "Italian", "French"));
		genderInput.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
	}
	
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage; 
        Parent root;
        if (event.getSource() == submit){       
           currentPerson.editPerson(nameArea.getText(), cultureArea.getValue(), occupationArea.getValue(), genderInput.getValue(), bioArea.getText());
           DS.savePeople();
           DS.saveConnections();
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
