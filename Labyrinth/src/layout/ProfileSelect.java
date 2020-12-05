package layout;

import backend.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class lets the user select which profile they are and also allows profiles to be deleted.
 * @author
 * @version 1.0
 */

public class ProfileSelect implements Initializable {

    @FXML private Button newProfileBtn;
    @FXML private Button selectProfileBtn;
    @FXML private Button deleteProfileBtn;
    @FXML private ListView profileList;

    private ArrayList<Profile> profiles;
    private int selectedIndex;
    private ArrayList<Profile> selectedProfiles = new ArrayList<>();

    public ProfileSelect(ArrayList<Profile> profiles){
        this.profiles = profiles;
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

        refreshProfiles();
    }


    private void refreshProfiles(){
        for (Profile i : profiles){
            profileList.getItems().add(i.getProfileName());
        }
    }
}
