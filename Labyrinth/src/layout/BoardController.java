package layout;

import Tiles.*;
import backend.*;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javax.print.DocFlavor;
import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML
    private GridPane topGrid;
    @FXML
    private GridPane rightGrid;
    @FXML
    private GridPane bottomGrid;
    @FXML
    private GridPane leftGrid;
    @FXML
    private GridPane tileGrid;

    //create new board 5x5
    private Level level;

    int size = 100;

    Image arrowDown = new Image(getClass().getResourceAsStream("/resources/arrowDOWN.png"));
    Image arrowUp = new Image(getClass().getResourceAsStream("/resources/arrowUP.png"));
    Image arrowLeft = new Image(getClass().getResourceAsStream("/resources/arrowLeft.png"));
    Image arrowRight = new Image(getClass().getResourceAsStream("/resources/arrowRight.png"));

    public BoardController(Level level){
        this.level = level;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupBoard();
        setupArrows();
        //refreshBoard();
    }

    private void setupBoard(){
        for (int j = 0; j < level.getBoardData().getColumnSize(); j++) {
            for (int k = 0; k < level.getBoardData().getRowSize(); k++) {

                //Loads tiles from SavedLevel.txt file
                System.out.println(level.getBoardData().getTileFromBoard(j,k).getType());
                ImageView tile = new ImageView("resources/" + level.getBoardData().getTileFromBoard(j,k).getType() + ".png");

                //sets tiles to specified size
                tile.setFitHeight(size);
                tile.setFitWidth(size);

                //rotates the tile depending on orientation
                tile.setRotate(level.getBoardData().getTileFromBoard(j,k).getOrientation());
                tileGrid.add(tile, j,k);
            }
        }
    }

    private void movePlayer(){

    }

    /**
     * the arrow is clicked
     */
    private void onClickArrow(int x, int y, Image arrow){
        Board.Cardinals c = Board.Cardinals.BOTTOM;
        if (arrow == arrowRight){
            c = Board.Cardinals.LEFT;
        }else if (arrow == arrowLeft){
            c = Board.Cardinals.RIGHT;
        }else if (arrow == arrowDown){
            c = Board.Cardinals.TOP;
        }

        System.out.println(x + "," + y);

        //FloorTile newTile = (FloorTile)F("TShaped", 0); //TODO, replace it later

        FloorTile newTile = (FloorTile)FileManager.createPlayerInventoryTiles("TShaped", 0); //TODO, replace it later
        FloorTile tile = level.getBoardData().placeOnNewTile(c, x, y, newTile);

        //show the board based on Board model
        //refreshBoard();
    }

    //Need to set arrows depending if tile is fixed or not
    //also need to create clickable arrows.
    public void setupArrows() {
        topGrid.setTranslateX(size);
        bottomGrid.setTranslateX(size);
        for (int x = 0; x < level.getBoardData().getColumnSize(); x++) {
            for (int y = 0; y < level.getBoardData().getRowSize(); y++) {
                if (x == 0) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowRight);
                    leftGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("arrowRight pressed ");
                            onClickArrow(xx, yy, arrowRight);
                            level.setTempX(xx);
                            level.setTempY(yy);
                            level.setTempCardinal(Board.Cardinals.RIGHT);
                            event.consume();
                        }
                    });

                } else if (x == level.getBoardData().getRowSize() - 1) { //4 is the board size, we will get board size from save files, this is just for testing right now.
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowLeft);
                    rightGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("arrowLeft pressed ");
                            onClickArrow(xx, yy, arrowLeft);
                            level.setTempX(xx);
                            level.setTempY(yy);
                            level.setTempCardinal(Board.Cardinals.LEFT);
                            event.consume();
                        }
                    });

                }
                if (y == 0) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowUp);
                    bottomGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("arrowUp pressed ");
                            onClickArrow(xx, yy, arrowUp);
                            level.setTempX(xx);
                            level.setTempY(yy);
                            level.setTempCardinal(Board.Cardinals.TOP);
                            event.consume();
                        }
                    });

                } else if (y == level.getBoardData().getColumnSize() - 1) { //4 is the board size, we will get board size from save files, this is just for testing right now.
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowDown);
                    topGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println("arrowDown pressed ");
                            onClickArrow(xx, yy, arrowDown);
                            level.setTempX(xx);
                            level.setTempY(yy);
                            level.setTempCardinal(Board.Cardinals.BOTTOM);
                            event.consume();
                        }
                    });
                }
            }
        }
    }
}

