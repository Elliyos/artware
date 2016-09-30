/**
 * Progress: WIP
 */

package downey.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ArtWARE");

        initRootLayout();

        showMainMenu();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showMainMenu() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("MainMenu.fxml"));
            AnchorPane mainMenu = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(mainMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays profile options page. Mapped to "People" button on MainMenu.FXML.
     */
    /**
     * Shows the person overview inside the root layout.
     */
    public void showProfileOptions() {
        try {
            // Load profile options.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ProfileOptions.fxml"));
            AnchorPane profileOptions = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(profileOptions);

            // Give the controller access to the main app.
            MainMenuController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}