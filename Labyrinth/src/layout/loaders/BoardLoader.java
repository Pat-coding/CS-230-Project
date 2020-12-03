package layout.loaders;

import backend.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import layout.controllers.BoardController;

import java.io.IOException;

public class BoardLoader {

    public BoardLoader(Stage stage, Level level){
        Stage primaryStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            BoardController controller = new BoardController(level);
            loader.setController(controller);
            Parent root = loader.load(getClass().getClassLoader().getResource("layout/fxml/Board.fxml").openStream());
            Scene scene = new Scene(root, 1280, 720);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
