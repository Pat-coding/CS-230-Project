package layout;

import Tiles.*;
import backend.*;

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

    @FXML private GridPane topGrid;
    @FXML private GridPane rightGrid;
    @FXML private GridPane bottomGrid;
    @FXML private GridPane leftGrid;
    @FXML private GridPane tileGrid;
    @FXML private Button saveGameBtn;
    @FXML private Button quitBtn;
    @FXML private Button drawTileBtn;
    @FXML private Button endTurnBtn;
    @FXML private ImageView backTrackImg;
    @FXML private ImageView FireTileImg;
    @FXML private ImageView IceTileImg;
    @FXML private ImageView doubleMoveImg;

    private Level level;
    private Player[] player;
    private int gameTurn;
    private SilkBag silkBag;
    private int playerIndex;
    private GameFlow gameFlow;

    int size = 100;

    Image arrowDown = new Image(getClass().getResourceAsStream("/resources/arrowDOWN.png"));
    Image arrowUp = new Image(getClass().getResourceAsStream("/resources/arrowUP.png"));
    Image arrowLeft = new Image(getClass().getResourceAsStream("/resources/arrowLeft.png"));
    Image arrowRight = new Image(getClass().getResourceAsStream("/resources/arrowRight.png"));

    public BoardController(Level level){
        this.level = level;
        //  This sets the turn to the player who is playing.
        this.playerIndex = 0;
        //  Provides gameFlow with the level information as well as the information regarding the who's turn it is.
        this.gameFlow = new GameFlow(this.level, this.playerIndex);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IceTileImg.setImage(arrowDown);
        FireTileImg.setImage(arrowDown);
        backTrackImg.setImage(arrowDown);
        doubleMoveImg.setImage(arrowDown);
        saveGameBtn.setOnAction(event -> {
            level.saveButtonFlag = true;
            gameFlow.flow();
        });
        quitBtn.setOnAction(event -> {
            System.exit(404);
        });
        drawTileBtn.setOnAction(event -> {
            this.level.drawTileFlag = true;
            gameFlow.flow();
        });
        endTurnBtn.setOnAction(event -> {
            level.endTurnFlag = true;
            gameFlow.flow();
        });
        setupBoard();
        setupArrows();
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

        for (int j = 0; j < level.getBoardData().getColumnSize(); j++) {
            for (int k = 0; k < level.getBoardData().getRowSize(); k++) {

                //Loads tiles from SavedLevel.txt file
                //System.out.println(level.getBoardData().getTileFromBoard(j,k).getType());
                ImageView tile = new ImageView("resources/playerImg.png");

                //sets tiles to specified size
                tile.setFitHeight(size);
                tile.setFitWidth(size);

                //rotates the tile depending on orientation
                tileGrid.add(tile, j,k);
            }
        }

    }

    /**
     * the arrow is clicked
     */
    private void onClickArrow(int x, int y, Image arrow){
        //  Removes every thing on the screen
        tileGrid.getChildren().removeAll();

        //  For debugging
        System.out.println(x + "," + y);

        level.setTempX(x);
        level.setTempY(y);
        gameFlow.flow();

        refreshBoard();
    }

    public void setupArrows() {
        topGrid.setTranslateX(size);
        bottomGrid.setTranslateX(size);
        for (int x = 0; x < level.getBoardData().getRowSize(); x++) {
            for (int y = 0; y < level.getBoardData().getColumnSize(); y++) {
                if (x == level.getBoardData().getRowSize() - 1) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowLeft);
                    rightGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        refreshBoard();
                        System.out.println("arrow facing left pressed ");
                        onClickArrow(xx, yy, arrowRight);
                        event.consume();
                    });

                } else if (x == 0) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowRight);
                    leftGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        refreshBoard();
                        System.out.println("arrow facing right pressed");
                        onClickArrow(xx, yy, arrowLeft);
                        level.setTempCardinal(Board.Cardinals.RIGHT);
                        event.consume();
                    });

                }
                if (y == 0) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowDown);
                    topGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        refreshBoard();
                        System.out.println("arrow facing down pressed");
                        onClickArrow(xx, yy, arrowUp);
                        level.setTempCardinal(Board.Cardinals.BOTTOM);
                        event.consume();
                    });

                } else if (y == level.getBoardData().getColumnSize() - 1) { //4 is the board size, we will get board size from save files, this is just for testing right now.
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowUp);
                    bottomGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        refreshBoard();
                        System.out.println("arrow facing up pressed");
                        onClickArrow(xx, yy, arrowDown);
                        level.setTempCardinal(Board.Cardinals.TOP);
                        event.consume();
                    });
                }
            }
        }
    }
}

