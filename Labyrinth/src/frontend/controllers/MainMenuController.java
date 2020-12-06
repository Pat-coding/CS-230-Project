package frontend.controllers;
import backend.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import frontend.loaders.LeaderboardLoader;
import frontend.loaders.LoadMenuLoader;
import frontend.loaders.ProfileSelectLoader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main menu controller for navigating around the program. This will be the first program user be seeing.
 *
 * @author Deniz Oral
 */
public class MainMenuController implements Initializable {

    private Stage primaryStage;

    public void setWindow(Stage stage){
        this.primaryStage = stage;
    }

    /**
     * Initializes the contents of gui from the MainMenu.fxml file.
     */
    @FXML private Label motdText;
    @FXML private BorderPane rootPane;

    /**
     * Initializes the main menu window.
     * @param location of fxml
     * @param resources of resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        Motd motd = new Motd();
        motdText.setText(motd.getMessage());
        System.out.println("Motd loaded");
    }

    /**
     * Launches a new game window.
     */
    public void launchNewGame(){
        ProfileSelectLoader loader = new ProfileSelectLoader(primaryStage, Level.profileArray, Level.newLevels);
    }

    /**
     * Launches a load game window.
     */
    public void launchLoadGame() {
        LoadMenuLoader loadMenuLoader = new LoadMenuLoader(primaryStage, Level.savedLevels);

    }

    /**
     * Launches a leaderboards window.
     */
    public void launchLeaderBoards() {
          LeaderboardLoader leaderboards = new LeaderboardLoader(primaryStage, Level.profileArray);

    }
}
