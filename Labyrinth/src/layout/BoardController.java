package layout;

import Tiles.*;
import backend.*;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;

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
    @FXML
    private Button saveGameBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private Button drawTileBtn;
    @FXML
    private Button endTurnBtn;
    @FXML
    private ImageView backTrackImg;
    @FXML
    private ImageView FireTileImg;
    @FXML
    private ImageView IceTileImg;
    @FXML
    private ImageView doubleMoveImg;

    private boolean saveButtonFlag = false;

    //create new board 5x5
    private Level level;
    private GameFlow flow;

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
        //Inventory bag will be hidden when endTurnBtn is pressed.
        IceTileImg.setImage(arrowDown);
        FireTileImg.setImage(arrowDown);
        backTrackImg.setImage(arrowDown);
        doubleMoveImg.setImage(arrowDown);


        setupBoard();
        setupArrows();

        saveGameBtn.setOnAction(event -> {
            saveGame();
        });

        quitBtn.setOnAction(event -> {

        });

        drawTileBtn.setOnAction(event -> {

        });

        endTurnBtn.setOnAction(event -> {

        });


    }

    public void gameStart() {
        for (int i = 0; i < level.getPlayerData().length; i++) {
            flow.flow(i);
        }
    }

    private void setupBoard(){
        for (int j = 0; j < level.getBoardData().getColumnSize(); j++) {
            for (int k = 0; k < level.getBoardData().getRowSize(); k++) {

                //Loads tiles from SavedLevel.txt file
                //System.out.println(level.getBoardData().getTileFromBoard(j,k).getType());
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

    public void refreshBoard() {
        for (int j = 0; j < level.getBoardData().getColumnSize(); j++) {
            for (int k = 0; k < level.getBoardData().getRowSize(); k++) {

                //Loads tiles from SavedLevel.txt file
                //System.out.println(level.getBoardData().getTileFromBoard(j,k).getType());
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

    /**
     * the arrow is clicked
     */
    private void onClickArrow(int x, int y, Image arrow){
        Board.Cardinals c = Board.Cardinals.BOTTOM;
        if (arrow == arrowDown){
            c = Board.Cardinals.LEFT;
        }else if (arrow == arrowLeft){
            c = Board.Cardinals.RIGHT;
        }else if (arrow == arrowDown){
            c = Board.Cardinals.TOP;
        }
        System.out.println(x + "," + y);

        //FloorTile newTile = (FloorTile)F("TShaped", 0); //TODO, replace it later

        FloorTile newTile = (FloorTile)FileManager.createPlayerInventoryTiles("TShaped", 0); //TODO, replace it later
        level.getBoardData().placeOnNewTile(c, x, y, newTile);


        //show the board based on Board model
        setupBoard();
    }

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

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        System.out.println("arrowRight pressed ");
                        onClickArrow(xx, yy, arrowRight);
                        level.setTempCardinal(Board.Cardinals.RIGHT);
                        event.consume();
                    });

                } else if (x == level.getBoardData().getRowSize() - 1) { //4 is the board size, we will get board size from save files, this is just for testing right now.
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowLeft);
                    rightGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        System.out.println("arrowLeft pressed ");
                        onClickArrow(xx, yy, arrowLeft);
                        level.setTempCardinal(Board.Cardinals.LEFT);
                        event.consume();
                    });

                }
                if (y == 0) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowUp);
                    bottomGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        System.out.println("arrowUp pressed ");
                        onClickArrow(xx, yy, arrowUp);
                        ;
                        level.setTempCardinal(Board.Cardinals.TOP);
                        event.consume();
                    });

                } else if (y == level.getBoardData().getColumnSize() - 1) { //4 is the board size, we will get board size from save files, this is just for testing right now.
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowDown);
                    topGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        System.out.println("arrowDown pressed ");
                        onClickArrow(xx, yy, arrowDown);
                        level.setTempCardinal(Board.Cardinals.BOTTOM);
                        event.consume();
                    });
                }
            }
        }
    }

//    public void onClickArrow(int x, int y, String direction, Image arrow){
//        Board.Cardinals c = Board.Cardinals.BOTTOM;
//        if (arrow == arrowRight){
//            c = Board.Cardinals.LEFT;
//        }else if (arrow == arrowLeft){
//            c = Board.Cardinals.RIGHT;
//        }else if (arrow == arrowDown){
//            c = Board.Cardinals.TOP;
//        }
//        image.setOnMouseClicked(e -> {
//            FloorTile newTile = (FloorTile)FileManager.createPlayerInventoryTiles("TShaped", 0);
//            //FloorTile tile = level.getBoardData().placeOnNewTile()
//        });
//    }

        public boolean saveGameCheck () {
            //  In range of amount of levels in saved levels
            for (int i = 0; i < Level.getSavedLevels().size(); i++) {
                //  If name is equal to a level in saved level.
                if (Level.getSavedLevels().get(i).getBoardData().getNameOfBoard().equals
                        (this.level.getBoardData().getNameOfBoard())) {
                    Level.getSavedLevels().remove(i);
                    Level.getSavedLevels().add(this.level);
                    return true;
                }
            }
            return false;
        }

        public void saveGame () {
            saveButtonFlag = true;
            //  Override previous save game
            if (!saveGameCheck()) {
                Level.getSavedLevels().add(this.level);
            }
        }

        public void drawTile () {

        }
    }

