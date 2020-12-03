package layout;

import backend.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileSelect implements Initializable {

    @FXML private Button newProfileBtn;
    @FXML private Button selectProfileBtn;
    @FXML private Button deleteProfileBtn;
    @FXML private ListView profileList;
    @FXML private Button menuBtn;

    private ArrayList<Profile> profiles;
    private int selectedIndex;
    private ArrayList<Profile> selectedProfiles = new ArrayList<>();

    private Stage stage;

    public ProfileSelect(Stage stage, ArrayList<Profile> profiles){
        this.profiles = profiles;
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newProfileBtn.setOnAction(e -> {

        });

        selectProfileBtn.setOnAction(e -> {
            selectedIndex = profileList.getSelectionModel().getSelectedIndex();
            if (selectedIndex<0){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Select a profile.");
                alert.showAndWait();
            } else {
                selectedProfiles.add(profiles.get(selectedIndex));
                if (selectedProfiles.size()==4){
                    System.out.println("4 Profiles loaded");

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
}
