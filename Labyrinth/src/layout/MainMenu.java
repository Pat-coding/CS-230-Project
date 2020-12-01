package layout;
import backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenu {

    private Stage primaryStage;

//    public MainMenu(Stage stage){
//        menu = stage;
//    }

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
        //rootPane.setStyle("-fx-background-color: #202020;");
    }

    //Opens new LaunchNewGame window
    public void launchNewGame(javafx.event.ActionEvent actionEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
        //Profile profile = new Profile("sad",1,2);
        //Board board = new Board("Title", new int[]{5,5});
//        BoardController controller = new BoardController(board);
//        loader.setController(controller);
//        Pane root = loader.load(getClass().getClassLoader().getResource("TestBoard2.fxml").openConnection());
        ArrayList<Level> savedLevels = FileManager.readLevelDataFile("SavedLevel.txt", "Saved Level");

        Game game = new Game(primaryStage, savedLevels.get(0));


//        BorderPane pane = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
//        rootPane.getChildren().setAll(pane);
        //BorderPane pane = FXMLLoader.load(getClass().getResource("TestBoard2.fxml")); //testing board
        //rootPane.getChildren().setAll(pane);

    }

    //Opens new launchLoadGame window
    public void launchLoadGame(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getResource("LoadGame.fxml"));
        rootPane.getChildren().setAll(pane);
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
