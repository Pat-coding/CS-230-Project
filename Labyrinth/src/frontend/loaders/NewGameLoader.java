package frontend.loaders;

import backend.Level;
import backend.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.controllers.LoadNewGameController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Class creates the Window which the NewGame is loaded in.
 * @author Deniz Oral
 * @version 1.0
 */

public class NewGameLoader {
    /**
     *  Loads the New Game Window.
     * @param stage primary stage
     * @param profiles containing all profiles
     * @param newLevel containing all levels
     */
    public NewGameLoader(Stage stage, ArrayList<Profile> profiles, ArrayList<Level> newLevel){
        Stage primaryStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            LoadNewGameController controller = new LoadNewGameController(stage,profiles,newLevel);
            loader.setController(controller);
            Parent root = loader.load(getClass().getClassLoader().getResource("frontend/fxml/LoadNewLevel.fxml").openStream());
            Scene scene = new Scene(root, 600, 390);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
