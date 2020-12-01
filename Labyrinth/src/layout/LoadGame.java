package sample;

import backend.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import layout.Game;
import layout.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class LoadGame implements Initializable {

    @FXML
    ListView<String> savedLevels;

    @FXML
    private Button backToMenu;
    @FXML
    private Button loadGameBtn;
    @FXML
    private Button deleteGameBtn;

    private ArrayList<Level> level;

    private Stage stage;

    public LoadGame(Stage stage, ArrayList<Level> level) {
        this.level = level;
        this.stage = stage;
    }

    int selectedIndex;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(level.size());
        //savedLevels.getItems().add(level.get(0));

        backToMenu.setOnAction(e -> {
            goToMenu();
        });

        loadGameBtn.setOnAction(actionEvent -> {
            loadGame();
        });

        //
        deleteGameBtn.setOnAction(e -> {
            deleteSaveFile();
        });

        refreshSaveList();
    }

    private void refreshSaveList() {
        for (Level i : level) {
            savedLevels.getItems().add(i.getBoardData().getNameOfBoard());
        }
    }

    private void deleteSaveFile(){
        selectedIndex = savedLevels.getSelectionModel().getSelectedIndex();
        if (selectedIndex<0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please select a save file before proceeding.");
            alert.showAndWait();
        } else {
            savedLevels.getItems().remove(selectedIndex);
        }
    }

    //TODO find a better way
    public void goToMenu() {
        try {
            Main main = new Main();
            main.start(stage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadGame(){
        selectedIndex = savedLevels.getSelectionModel().getSelectedIndex();
        if (selectedIndex<0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please select a save file before proceeding.");
            alert.showAndWait();
        } else {
            selectedIndex = savedLevels.getSelectionModel().getSelectedIndex();
            System.out.println(selectedIndex);
            Game game = new Game(stage, level.get(0));
        }
    }
}
