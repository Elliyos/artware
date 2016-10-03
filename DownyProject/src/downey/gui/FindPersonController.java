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

public class FindPersonController {

	@FXML
	ListView<String> list = new ListView<String>();
	@FXML
	private Button goBack;
	
	DataStorage DS;
	ArrayList<Person> peopleList;
	
    public FindPersonController() {
    }
    
    @FXML
    private void initialize() {
    	peopleList = DS.getPeopleArray();
    	String names = nameList(peopleList);
    	ObservableList<String> people =FXCollections.observableArrayList (names);
    	list.setOrientation(Orientation.VERTICAL);
    	list.setItems(people);
    }
    
    public String nameList(ArrayList<Person> peopleList){
    	String names = "";
    	for (int i = 0; i <= peopleList.size()-1; i++){
    		Person currentPerson = peopleList.get(i);
    		names += "\"" + currentPerson.getName() + "\", ";
    	}
    	return names;
    }
	
	
}
