package frontend.controllers;

import backend.Level;
import backend.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import frontend.Main;
import frontend.loaders.NewGameLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileSelectController implements Initializable {

    @FXML private Button newProfileBtn;
    @FXML private Button selectProfileBtn;
    @FXML private Button deleteProfileBtn;
    @FXML private ListView profileList;
    @FXML private Button menuBtn;
    @FXML private Label loadedProfiles;
    @FXML private RadioButton twoPlayers;
    @FXML private RadioButton fourPlayers;

    private int selectedIndex;

    private ArrayList<Profile> profiles;
    private ArrayList<Level> newLevel;

    private ArrayList<Profile> selectedProfiles = new ArrayList<>();

    private Stage stage;

    public ProfileSelectController(Stage stage, ArrayList<Profile> profiles, ArrayList<Level> newLevel){
        this.profiles = profiles;
        this.newLevel = newLevel;
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadedProfiles.setText("No profiles selected");
        loadedProfiles.setTextFill(Color.web("#ff0000", 0.8));

        newProfileBtn.setOnAction(e -> {

        });

        selectProfileBtn.setOnAction(e -> {
            selectedIndex = profileList.getSelectionModel().getSelectedIndex();
            if (selectedIndex<0){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Select a profile.");
                alert.showAndWait();
            } else {
//                selectedProfiles
                loadedProfiles.setText(getSelectedProfiles().size()+1+" Profile(s) loaded");
                loadedProfiles.setTextFill(Color.web("#008000", 0.8));
                getSelectedProfiles().add(profiles.get(selectedIndex));
                if (twoPlayers.isSelected() && getSelectedProfiles().size() == 2){
                    selectProfileBtn.setDisable(true);
                    System.out.println("2 Profiles loaded");
                    NewGameLoader newGame = new NewGameLoader(stage, selectedProfiles, newLevel);
                } else if (fourPlayers.isSelected() && getSelectedProfiles().size() == 4){
                    selectProfileBtn.setDisable(true);
                    System.out.println("4 Profiles loaded");
                    NewGameLoader newGame = new NewGameLoader(stage, selectedProfiles, newLevel);
                }
            }
        });


        /**
         * Deletes selected profile from list
         */
        deleteProfileBtn.setOnAction(e -> {
            selectedIndex = profileList.getSelectionModel().getSelectedIndex();
            if (selectedIndex<0){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Select a profile from list before deleting it.");
                alert.showAndWait();
            } else {
                profileList.getItems().remove(selectedIndex);
            }
        });


        menuBtn.setOnAction(event -> {
            try {
                Main main = new Main();
                main.start(stage);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        refreshProfiles();
    }


    private void refreshProfiles(){
        for (Profile i : profiles){
            profileList.getItems().add(i.getProfileName());
        }
    }

    public ArrayList<Profile> getSelectedProfiles() {
        return selectedProfiles;
    }

}
