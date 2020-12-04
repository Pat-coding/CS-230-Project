package frontend.loaders;

import backend.Level;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.controllers.LoadLevelController;


import java.io.IOException;
import java.util.ArrayList;


public class LoadMenuLoader {
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
