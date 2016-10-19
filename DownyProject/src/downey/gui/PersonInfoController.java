package downey.gui;

import java.io.IOException;
import java.util.ArrayList;

import downey.main.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class PersonInfoController extends FindPersonController{
	
	private MainApp mainApp;
	//private Person selectedPerson = super.getSelectedPerson();
	private DataStorage DS = DataStorage.getMainDataStorage();
	private ArrayList<Person> peopleList = DS.getPeopleArray();
	
	@FXML
	private Button goBack;
	@FXML
	private Button editButton;
	@FXML
	private Label nameLabel;
	@FXML
	private Label occupationLabel;
	@FXML
	private Label cultureLabel;
	@FXML
	private Label genderLabel;
	@FXML
	private Label bioLabel;
	
	
	

	public PersonInfoController() {

    }
    
    
	/**
	 * This method is currently bugged. Index isn't returning correctly;
	 * therefore, only one person is being included to show core functionality
	 * of information page.
	 * @throws IOException
	 */
	@FXML
    private void initialize() throws IOException {
    	Person chosenPerson = DS.getPersonObject(DS.getSelectedName());
    	nameLabel.setText(chosenPerson.getName());
    	occupationLabel.setText(chosenPerson.getOccupation());
    	cultureLabel.setText(chosenPerson.getCulture());
    	genderLabel.setText(chosenPerson.getGender());
    	bioLabel.setText(chosenPerson.getBio());
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage; 
        Parent root;
        if (event.getSource() == goBack){       
           stage=(Stage) goBack.getScene().getWindow();
           root = FXMLLoader.load(getClass().getResource("FindPerson.fxml"));
         } else {
        	 stage=(Stage) editButton.getScene().getWindow();
        	 root = FXMLLoader.load(getClass().getResource("EditInfo.fxml"));
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
