package frontend.loaders;

import backend.Level;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.controllers.LoadLevelController;


import java.io.IOException;
import java.util.ArrayList;


/**
 * The Class creates the Window which the Menu is loaded in.
 * @author Deniz Oral
 * @version 1.0
 */

public class LoadMenuLoader {

    /**
     * Loads the Menu Window
     * @param stage the primary stage
     * @param level contains information on all levels
     */
    public LoadMenuLoader(Stage stage, ArrayList<Level> level){
        Stage primaryStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            LoadLevelController controller = new LoadLevelController(stage, level);
            loader.setController(controller);
            Parent root = loader.load(getClass().getClassLoader().getResource("frontend/fxml/LoadLevel.fxml").openStream());
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
