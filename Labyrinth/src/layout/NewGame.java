//Sample code to test the connections between Menu

package layout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class NewGame {

    @FXML
    private BorderPane rootPane;

    @FXML
    private void initialize(){
        //This is a check to see if window is loaded.
        System.out.println("Loaded New Game window.");
    }


    public void goBackToMenu(ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}