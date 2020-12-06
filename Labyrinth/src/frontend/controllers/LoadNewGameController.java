package frontend.controllers;

import backend.GameFlow;
import backend.Level;
import backend.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import frontend.loaders.BoardLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Load new game class. Will let user load new games and create users.
 *
 * @author Deniz Oral
 */
public class LoadNewGameController implements Initializable {

    /**
     * Initializing the contents of the gui from the LoadNewLevel.fxml file.
     */
    @FXML private Button selectBtn;
    @FXML private ListView mapList;

    private ArrayList<Profile> profiles;
    private ArrayList<Level> newLevel;

    private int selectedIndex;
    private Stage stage;

    /**
     * Constructor for LoadNewGameController.
     * @param stage takes in stage.
     * @param profiles takes in profiles array.
     * @param newLevel takes in newLevel array.
     */
    public LoadNewGameController(Stage stage, ArrayList<Profile> profiles, ArrayList<Level> newLevel){
        this.profiles = profiles;
        this.newLevel = newLevel;
        this.stage = stage;

    }

    /**
     * Initializer for the class.
     * @param location of fxml
     * @param resources of resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectBtn.setOnAction(actionEvent -> {
            loadGame();
        });

        refreshMapList();
    }

    /**
     * Loads the selected game.
     */
    private void loadGame(){
        selectedIndex = mapList.getSelectionModel().getSelectedIndex();
        //checks if the user has selected item
        if (selectedIndex<0){
            //shows error messages
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please select a save file before proceeding.");
            alert.showAndWait();
        } else {
            //displays the selected level
            selectedIndex = mapList.getSelectionModel().getSelectedIndex();

            for(int i = 0; i < newLevel.get(selectedIndex).getBoardData().getColumnSize(); i++) {
                for (int j = 0; j < newLevel.get(selectedIndex).getBoardData().getRowSize(); j++) {
                    if (newLevel.get(selectedIndex).getBoardData().getTileFromBoard(i, j) == null) {
                        newLevel.get(selectedIndex).getBoardData().insertTile(i, j, newLevel.get(selectedIndex).getSilkBag().populateRandomBoardTiles());
                    }
                }
            }
            GameFlow.initiatePlayers(profiles , newLevel.get(selectedIndex));
            BoardLoader game = new BoardLoader(stage, newLevel.get(selectedIndex));
        }
    }

    /**
     * Populates the board with new data.
     */
    public void refreshMapList(){
        for (Level i : newLevel){
            mapList.getItems().add(i.getBoardData().getNameOfBoard());
        }
    }
}
