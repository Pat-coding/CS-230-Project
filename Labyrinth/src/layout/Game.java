package layout;

import backend.Board;
import backend.Level;
import backend.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game {

    public Game(Stage stage, Level level){
        Stage primaryStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            BoardController controller = new BoardController(level);
            loader.setController(controller);
            Parent root = loader.load(getClass().getClassLoader().getResource("layout/TestBoard2.fxml").openStream());
            Scene scene = new Scene(root, 1280, 720);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
