package layout;

import backend.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author
 * @version 1.0
 */

public class ProfileSelectLoader {
    private Stage primaryStage;

    public ProfileSelectLoader(Stage stage, ArrayList<Profile> profiles){
        primaryStage = stage;
        try {
            FXMLLoader fxmlloader = new FXMLLoader();
            ProfileSelect controller = new ProfileSelect(profiles);
            fxmlloader.setController(controller);
            Parent root = fxmlloader.load(getClass().getClassLoader().getResource("layout/ProfileSelect.fxml").openStream());
            Scene scene = new Scene(root, 530, 530);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
