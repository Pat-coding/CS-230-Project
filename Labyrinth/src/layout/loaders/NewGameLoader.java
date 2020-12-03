package layout.loaders;

import backend.Level;
import backend.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import layout.controllers.LoadNewGameController;

import java.io.IOException;
import java.util.ArrayList;

public class NewGameLoader {

    public NewGameLoader(Stage stage, ArrayList<Profile> profiles, ArrayList<Level> newLevel){
        Stage primaryStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            LoadNewGameController controller = new LoadNewGameController(stage,profiles,newLevel);
            loader.setController(controller);
            Parent root = loader.load(getClass().getClassLoader().getResource("layout/fxml/LoadNewLevel.fxml").openStream());
            Scene scene = new Scene(root, 600, 390);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
