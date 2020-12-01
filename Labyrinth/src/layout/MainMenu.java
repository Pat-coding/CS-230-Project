package layout;
import backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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

    ArrayList<Level> savedLevels = FileManager.readLevelDataFile("SavedLevel.txt", "Saved Level");


    //when menu is launched, initialize motd and display it
    @FXML
    private void initialize(){
        Motd motd = new Motd();
        motdText.setText(motd.getMessage());
        System.out.println("Motd loaded");
    }

    //Opens new LaunchNewGame window
    public void launchNewGame(javafx.event.ActionEvent actionEvent) throws IOException {
        Game game = new Game(primaryStage, savedLevels.get(0));
    }

    //Opens new launchLoadGame window
    public void launchLoadGame(javafx.event.ActionEvent actionEvent) throws IOException {
        LoadMenuLoader loadMenuLoader = new LoadMenuLoader(primaryStage, savedLevels);

    }

    //Opens new launchLeaderBoards window
    public void launchLeaderBoards(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getResource("Leaderboards.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    public void launchInventory(javafx.event.ActionEvent actionEvent) throws IOException{
        BorderPane pane = FXMLLoader.load(getClass().getResource("InventoryController.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
