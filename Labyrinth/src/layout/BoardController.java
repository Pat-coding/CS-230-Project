package layout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    @FXML
    Pane tiles;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image t_shape = new Image("T_SHAPE_PLACEHOLDER.png");
        int size = 100;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                final ImageView tileView = new ImageView();
                tileView.setFitHeight(size);
                tileView.setFitWidth(size);
                tileView.setImage(t_shape);
                tileView.setTranslateX(x * size);
                tileView.setTranslateY(y * size);
                tiles.getChildren().add(tileView);
            }
        }

    }
}
