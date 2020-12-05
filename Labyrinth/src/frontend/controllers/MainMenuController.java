package frontend.controllers;
import backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import frontend.loaders.LeaderboardLoader;
import frontend.loaders.LoadMenuLoader;
import frontend.loaders.ProfileSelectLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private Stage primaryStage;

    public void setStage(Stage stage){

        this.primaryStage = stage;
    }

    //This will import the label from fxml
    @FXML
    private Label motdText;

    //this will import the borderpane from fxml
    @FXML
    private BorderPane rootPane;

    //when menu is launched, initialize motd and display it
    @Override
    public void initialize(URL location, ResourceBundle resources){
        Motd motd = new Motd();
        motdText.setText(motd.getMessage());
        System.out.println("Motd loaded");
    }

    //Opens new LaunchNewGame window
    public void launchNewGame(){
        ProfileSelectLoader loader = new ProfileSelectLoader(primaryStage, Level.profileArray, Level.newLevels);
    }

    //Opens new launchLoadGame window
    public void launchLoadGame() {
        LoadMenuLoader loadMenuLoader = new LoadMenuLoader(primaryStage, Level.savedLevels);

    }

    //Opens new launchLeaderBoards window
    public void launchLeaderBoards() {
          LeaderboardLoader leaderboards = new LeaderboardLoader(primaryStage, Level.profileArray);

    }
}
