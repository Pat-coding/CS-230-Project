package frontend.loaders;

import backend.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import frontend.controllers.BoardController;

import java.io.IOException;

/**
 * The Class creates the Window which the Board is loaded in.
 * @author Deniz Oral
 * @version 1.0
 */

public class BoardLoader {


    /**
     * Creates a Board window with all relevant information.
     * @param stage the primary window for this stage
     * @param level contains all information needed for a board
     */
    public BoardLoader(Stage stage, Level level){
        Stage primaryStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            BoardController controller = new BoardController(level);
            loader.setController(controller);
            Parent root = loader.load(getClass().getClassLoader().getResource("frontend/fxml/Board.fxml").openStream());
            Scene scene = new Scene(root, 1280, 720);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
