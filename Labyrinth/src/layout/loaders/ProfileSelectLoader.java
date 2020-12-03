package layout.loaders;

import backend.Level;
import backend.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import layout.controllers.ProfileSelectController;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileSelectLoader {
    private Stage primaryStage;

    public ProfileSelectLoader(Stage stage, ArrayList<Profile> profiles, ArrayList<Level> newLevel){
        primaryStage = stage;
        try {
            FXMLLoader fxmlloader = new FXMLLoader();
            ProfileSelectController controller = new ProfileSelectController(primaryStage, profiles, newLevel);
            fxmlloader.setController(controller);
            Parent root = fxmlloader.load(getClass().getClassLoader().getResource("layout/fxml/ProfileSelect.fxml").openStream());
            Scene scene = new Scene(root, 530, 530);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
