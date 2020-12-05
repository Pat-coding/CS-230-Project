package layout;

import backend.Leaderboard;
import backend.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * this method IDK
 * @author
 * @version 1.0
 */

public class LeaderboardLoader {

    private Stage stage;
    private ArrayList<Profile> profiles;

    public LeaderboardLoader(Stage stage, ArrayList<Profile> profiles) {
        this.stage = stage;
        this.profiles = profiles;

        try {
            FXMLLoader loader = new FXMLLoader();
            Leaderboards controller = new Leaderboards(stage, profiles);
            loader.setController(controller);
            Parent root = loader.load(getClass().getClassLoader().getResource("layout/Leaderboards.fxml").openStream());
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
