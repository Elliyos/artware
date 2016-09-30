package downey.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainMenuController {
	    
		@FXML
	    private Rectangle mainPersonButton;
	    @FXML
	    private Rectangle mainConnectionButton;

	    private MainApp mainApp;

	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public MainMenuController() {
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    
	    @FXML
	    private void initialize() {
	    }
	    
	    /*
	     * Currently having issues adding action to rectangles.
	     */
	    @FXML
	    private void handleButtonAction(ActionEvent event) throws IOException{
	        Stage stage; 
	        Parent root;
	        if(event.getSource()==this.mainPersonButton){
	           //get reference to the button's stage         
	           stage=(Stage) this.mainPersonButton.getScene().getWindow();
	           //load up OTHER FXML document
	     root = FXMLLoader.load(getClass().getResource("ProfileOptions.fxml"));
	         }
	        else{
	          stage=(Stage) this.mainConnectionButton.getScene().getWindow();
	     root = FXMLLoader.load(getClass().getResource("ConnectionOptions.fxml"));
	         }
	        //create a new scene with root and set the stage
	         Scene scene = new Scene(root);
	         stage.setScene(scene);
	         stage.show();
	       }
	    
	    
	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }
	}