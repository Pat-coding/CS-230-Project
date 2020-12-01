//Sample code to test the connections between Menu

package layout;

import backend.Leaderboard;
import backend.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import backend.Leaderboard;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Leaderboards implements Initializable {

    @FXML
    private Button backToMenuBtn;
    @FXML
    private MenuButton sortWins;
    @FXML
    private MenuBar sortLoss;
    @FXML
    private MenuButton gamesPlayed;
    @FXML
    private ListView<String> profileList;

    private Leaderboard leaderboard;
    private Stage stage;
    private ArrayList<Profile> profiles;

    public Leaderboards(Stage stage, ArrayList<Profile> profiles){
        this.profiles = profiles;
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backToMenuBtn.setOnAction(e -> {
            goBackToMenu();
        });

        sortWins.setOnAction(e -> {
            sortByWins();
        });
    }


//    public void displayProfiles() {
//        profiles
//    }


    public void sortByWins(){
        leaderboard.sort(profiles, 0, true);
        for (int i = 0; i<profiles.size(); i++) {
            //profileList.getItems().add(leaderboard.getLeaderboard().get());
        }
    }

    public void goBackToMenu() {
        try {
            Main main = new Main();
            main.start(stage);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}