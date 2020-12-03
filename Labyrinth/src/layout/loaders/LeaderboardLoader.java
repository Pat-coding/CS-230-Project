package layout.loaders;

import backend.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import layout.controllers.LeaderboardController;

import java.io.IOException;
import java.util.ArrayList;

public class LeaderboardLoader {

    private Stage stage;
    private ArrayList<Profile> profiles;

    public LeaderboardLoader(Stage stage, ArrayList<Profile> profiles) {
        this.stage = stage;
        this.profiles = profiles;

        try {
            FXMLLoader loader = new FXMLLoader();
            LeaderboardController controller = new LeaderboardController(stage, profiles);
            loader.setController(controller);
            Parent root = loader.load(getClass().getClassLoader().getResource("layout/fxml/Leaderboards.fxml").openStream());
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
