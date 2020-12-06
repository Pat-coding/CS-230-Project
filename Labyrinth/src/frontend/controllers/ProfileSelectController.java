package frontend.controllers;

import backend.FileManager;
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
    @FXML private RadioButton threePlayers;
    @FXML private RadioButton fourPlayers;
    @FXML private TextField nameField;


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
        //sets the toggle group for radio buttons.
        twoPlayers.setSelected(true);

        ArrayList<Integer> counter = new ArrayList<>();
        loadedProfiles.setText("No profiles selected");
        loadedProfiles.setTextFill(Color.web("#ff0000", 0.8));

        newProfileBtn.setOnAction(e -> {
            if (!nameField.getText().isEmpty()){
                profiles.add(new Profile(nameField.getText()));
                nameField.clear();
                refreshProfiles();
                FileManager.createNewProfile(Level.getProfileArray());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter something inside the box.");
                alert.showAndWait();
            }
        });

        /**
         * Selects player from the list provided to user
         */
        selectProfileBtn.setOnAction(e -> {
            selectedIndex = profileList.getSelectionModel().getSelectedIndex();
            //check if theres anything selected in a list
            if (selectedIndex<0){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please select a profile");
                alert.showAndWait();
            } else {
                if (!counter.contains(selectedIndex)) {

                    getSelectedProfiles().add(profiles.get(selectedIndex));
                    if (twoPlayers.isSelected() && getSelectedProfiles().size() == 2) {
                        selectProfileBtn.setDisable(true);
                        System.out.println("2 Profiles loaded");
                        NewGameLoader newGame = new NewGameLoader(stage, selectedProfiles, newLevel);
                    } else if (threePlayers.isSelected() && getSelectedProfiles().size() == 3) {
                        selectProfileBtn.setDisable(true);
                        System.out.println("3 Profiles loaded");
                        NewGameLoader newGame = new NewGameLoader(stage, selectedProfiles, newLevel);
                    } else if (fourPlayers.isSelected() && getSelectedProfiles().size() == 4) {
                        selectProfileBtn.setDisable(true);
                        System.out.println("4 Profiles loaded");
                        NewGameLoader newGame = new NewGameLoader(stage, selectedProfiles, newLevel);
                    }
                    loadedProfiles.setText(getSelectedProfiles().size()+" Profile(s) loaded");
                    loadedProfiles.setTextFill(Color.web("#008000", 0.8));
                    counter.add(selectedIndex);

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "This player is already in a list. " +
                            "Please select another player!");
                    alert.showAndWait();
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
                profiles.remove(selectedIndex);
                FileManager.createNewProfile(Level.getProfileArray());
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
        profileList.getItems().clear();
        for (Profile i : profiles){
            profileList.getItems().add(i.getProfileName());
        }
    }

    public ArrayList<Profile> getSelectedProfiles() {
        return selectedProfiles;
    }

}
