package frontend.controllers;

import backend.FileManager;
import backend.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import frontend.loaders.BoardLoader;
import frontend.Main;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Load level controller class. This class lets the user choose which previous saved game they want to load
 * @author Deniz Oral
 */
public class LoadLevelController implements Initializable {

    /**
     * Initializing the contents of the gui from the LoadLevel.fxml file.
     */
    @FXML ListView<String> savedLevels;
    @FXML Button backToMenu;
    @FXML Button loadGameBtn;
    @FXML Button deleteGameBtn;

    private ArrayList<Level> level;

    private Stage stage;

    int selectedIndex;

    /**
     * Constructor for load game
     * @param stage takes in primary stage
     * @param level takes in arraylist of levels
     */
    public LoadLevelController(Stage stage, ArrayList<Level> level) {
        this.level = level;
        this.stage = stage;
    }

    /**
     * this will the main method that will develop the actions for
     * the buttons
     * @param location location of fxml file
     * @param resources resource
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backToMenu.setOnAction(e -> goToMenu());

        loadGameBtn.setOnAction(actionEvent -> loadGame());

        deleteGameBtn.setOnAction(e -> deleteSaveFile());

        refreshSaveList();
    }

    /**
     * Refresh the display of saved levels
     */
    private void refreshSaveList() {
        //Add each country to the displayed list
        for (Level i : level) {
            savedLevels.getItems().add(i.getBoardData().getNameOfBoard());
        }
    }

    /**
     * Handles the behaviour of the delete save
     * This will delete the save file from the list and file
     */
    private void deleteSaveFile(){
        //Get the index of the selected save file from the list
        selectedIndex = savedLevels.getSelectionModel().getSelectedIndex();

        //check if user has selected an item
        if (selectedIndex<0){
            //shows error messages
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please select a save file before proceeding.");
            alert.showAndWait();
        } else {
            //removes save from the list
            savedLevels.getItems().remove(selectedIndex);
            level.remove(selectedIndex);
            FileManager.createNewSaveFile(Level.getSavedLevels());
        }
    }

    /**
     * Handles the transition back to the menu
     */
    public void goToMenu() {
        try {
            Main main = new Main();
            main.start(stage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles the load game button, will open the selected game save
     */
    public void loadGame(){
        selectedIndex = savedLevels.getSelectionModel().getSelectedIndex();

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
            selectedIndex = savedLevels.getSelectionModel().getSelectedIndex();
            System.out.println(selectedIndex);
            BoardLoader game = new BoardLoader(stage, level.get(selectedIndex));
        }
    }
}
