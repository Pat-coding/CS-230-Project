package layout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class BoardController implements Initializable {
    @FXML
    private GridPane boardControl;
    @FXML
    private Pane tiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image t_shape = new Image("T_SHAPE_PLACEHOLDER.png");
        Image straight = new Image("STRAIGHT_PLACEHOLDER.png");
        Image corner = new Image("CORNER_PLACEHOLDER.png");
        Image goal = new Image("GOAL_PLACEHOLDER.png");

        int size = 100;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                final ImageView tileView = new ImageView();
                tileView.setFitHeight(size);
                tileView.setFitWidth(size);
                tileView.setImage(straight);
                tileView.setTranslateX(x * size);
                tileView.setTranslateY(y * size);
                tiles.getChildren().add(tileView);
            }
        }
    }
}
