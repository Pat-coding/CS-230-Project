package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.controllers.MainMenuController;

/**
 * this class creates the window for the program to run on and launches main menu
 * @author Tom
 * @version 1.0
 */

public class Main extends Application {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;


    /**
     *  Creates the primary window for the application
     * @param primaryStage the primary window
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Labyrinth");
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("frontend/fxml/MainMenu.fxml").openStream());
            MainMenuController menu = loader.getController();
            menu.setStage(primaryStage);
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            scene.getStylesheets().add("Style.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the application extension
     * @param args a list of parameters
     */

    public static void main(String[] args) {
        launch(args);
    }
}