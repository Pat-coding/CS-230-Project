package frontend.controllers;

import backend.Leaderboard;
import backend.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import frontend.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for leaderboards where user will be able to sort users by wins, losses, win ratios and games played.
 *
 * @author Deniz Oral
 */
public class LeaderboardController implements Initializable {

    /**
     * Initializing the contents of the gui from the Leaderboards.fxml file.
     */
    @FXML private Button backToMenuBtn;
    @FXML private MenuItem sortWins;
    @FXML private MenuItem sortLoss;
    @FXML private MenuItem sortGamesPlayed;
    @FXML private MenuItem sortWinRatio;
    @FXML private ListView<String> profileList;

    private Leaderboard leaderboard;
    private Stage stage;
    private ArrayList<Profile> profiles;

    private ArrayList<Profile> sortedWins;
    private ArrayList<Profile> sortedLosses;
    private ArrayList<Profile> sortedGamesPlayed;
    private ArrayList<Profile> sortedWinRatio;

    /**
     * Constructor for leaderboards.
     * @param stage takes in stage window.
     * @param profiles takes in profiles array.
     */
    public LeaderboardController(Stage stage, ArrayList<Profile> profiles){
        this.profiles = profiles;
        this.stage = stage;
        sortedWins = new Leaderboard(profiles, 0, false).getLeaderboard();
        sortedLosses = new Leaderboard(profiles, 1, false).getLeaderboard();
        sortedWinRatio = new Leaderboard(profiles, 2, false).getLeaderboard();
        sortedGamesPlayed = new Leaderboard(profiles, 3, false).getLeaderboard();
    }

    /**
     * Initializer for Leaderboards class.
     * @param location location of fxml
     * @param resources location of resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backToMenuBtn.setOnAction(e -> goBackToMenu());

        sortWins.setOnAction(e -> sortByWins());

        sortLoss.setOnAction(event -> sortByLoss());

        sortWinRatio.setOnAction(event -> sortByWinRatio());

        sortGamesPlayed.setOnAction(e -> sortGamesPlayed());

        refreshLeaderboard();
    }

    /**
     * Populates the board with new data.
     */
    private void refreshLeaderboard(){
        profileList.getItems().clear();
        for(Profile i : profiles){
            profileList.getItems().add(i.getProfileName());
        }
    }

    /**
     * Sorts the data by wins.
     */
    public void sortByWins() {
        profileList.getItems().clear();
        System.out.println("Sorting by wins");
        for (Profile i : sortedWins){
            profileList.getItems().add(i.getProfileName());
        }
    }

    /**
     * Sorts the data by games played.
     */
    private void sortGamesPlayed() {
        profileList.getItems().clear();
        System.out.println("Sorting by games played");
        for (Profile i : sortedGamesPlayed)
            profileList.getItems().add(i.getProfileName());
    }

    /**
     * Sorts the data by losses.
     */
    private void sortByLoss() {
        profileList.getItems().clear();
        System.out.println("Sorting by losses");
        for (Profile i : sortedLosses)
            profileList.getItems().add(i.getProfileName());
    }

    /**
     * Sorts the data by win ratio.
     */
    private void sortByWinRatio(){
        profileList.getItems().clear();
        System.out.println("Sorting by winratio");
        for (Profile i : sortedWinRatio)
            profileList.getItems().add(i.getProfileName());
    }

    /**
     * Loads the main menu window.
     */
    public void goBackToMenu() {
        try {
            Main main = new Main();
            main.start(stage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}