package frontend.loaders;

import backend.Level;
import backend.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.controllers.ProfileSelectController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ProfileSelectLoader {
    private Stage primaryStage;

    protected ArrayList<Profile> profiles;
    protected ArrayList<Level> newLevel;

    public ProfileSelectLoader(Stage stage, ArrayList<Profile> profiles, ArrayList<Level> newLevel){

        this.profiles = profiles;
        this.newLevel = newLevel;

        primaryStage = stage;
        try {
            FXMLLoader fxmlloader = new FXMLLoader();
            ProfileSelectController controller = new ProfileSelectController(primaryStage, this.profiles, this.newLevel);
            fxmlloader.setController(controller);
            Parent root = fxmlloader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("frontend/fxml/ProfileSelect.fxml")).openStream());
            Scene scene = new Scene(root, 530, 530);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
