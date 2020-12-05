package layout;
import backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class creates the menu where users can decide what they want to do
 * @author
 * @version 1.0
 */

public class MainMenu {

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
    @FXML
    private void initialize(){
        Motd motd = new Motd();
        motdText.setText(motd.getMessage());
        System.out.println("Motd loaded");
    }

    //Opens new LaunchNewGame window
    public void launchNewGame(){
        ProfileSelectLoader loader = new ProfileSelectLoader(primaryStage, Level.profileArray);
        //Game game = new Game(primaryStage, savedLevels.get(0));
    }

    //Opens new launchLoadGame window
    public void launchLoadGame() {
        LoadMenuLoader loadMenuLoader = new LoadMenuLoader(primaryStage, Level.savedLevels);

    }

    //Opens new launchLeaderBoards window
    public void launchLeaderBoards() {
//        BorderPane pane = FXMLLoader.load(getClass().getResource("Leaderboards.fxml"));
//        rootPane.getChildren().setAll(pane);
          LeaderboardLoader leaderboards = new LeaderboardLoader(primaryStage, Level.profileArray);

    }
    public void launchInventory() throws IOException{
        BorderPane pane = FXMLLoader.load(getClass().getResource("InventoryController.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
