package layout.controllers;

import Tiles.*;
import backend.*;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.awt.*;

import javafx.scene.input.KeyEvent;
import java.awt.event.KeyListener;
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
    private EventHandler<KeyEvent> keyListener = event -> {
        if (event.getCode() == KeyCode.UP) {
            level.pressUpFlag = true;
            gameFlow.flow();
            refreshBoard();
            System.out.println("UP");
        } else if (event.getCode() == KeyCode.DOWN) {
            level.pressDownFlag = true;
            System.out.println("DOWN");
            gameFlow.flow();
            refreshBoard();
        } else if (event.getCode() == KeyCode.LEFT) {
            level.pressLeftFlag = true;
            System.out.println("LEFT");
            gameFlow.flow();
            refreshBoard();
        } else if (event.getCode() == KeyCode.RIGHT) {
            level.pressRightFlag = true;
            System.out.println("RIGHT");
            gameFlow.flow();
            refreshBoard();
        }
        event.consume();
    };
    int size = 100;
    Image arrowDown = new Image(getClass().getResourceAsStream("/resources/arrowDOWN.png"));
    Image arrowUp = new Image(getClass().getResourceAsStream("/resources/arrowUP.png"));
    Image arrowLeft = new Image(getClass().getResourceAsStream("/resources/arrowLeft.png"));
    Image arrowRight = new Image(getClass().getResourceAsStream("/resources/arrowRight.png"));
    public BoardController(Level level) {
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

        quitBtn.setOnAction(event -> System.exit(404));


        drawTileBtn.setOnAction(event -> {
            this.level.drawTileFlag = true;
            drawTileBtn.setOnKeyPressed(keyListener);
            gameFlow.flow();
            refreshBoard();
            event.consume();
        });
        endTurnBtn.setOnAction(event -> {
            level.endTurnFlag = true;
            gameFlow.flow();
        });
        setupBoard();
        setupArrows();
    }

    //Displays win message
    private void youWonCongrats(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You won :)");
        alert.showAndWait();
    }

    private void setupBoard() {
        for (int j = 0; j < level.getBoardData().getRowSize(); j++) {
            for (int k = 0; k < level.getBoardData().getColumnSize(); k++) {

                //Loads tiles from SavedLevel.txt file
                //System.out.println(level.getBoardData().getTileFromBoard(j,k).getType());
                ImageView tile = new ImageView("resources/" + level.getBoardData().getTileFromBoard(j, k).getType() + ".png");

                //sets tiles to specified size
                tile.setFitHeight(size);
                tile.setFitWidth(size);

                //rotates the tile depending on orientation
                tile.setRotate(level.getBoardData().getTileFromBoard(j, k).getOrientation());
                tileGrid.add(tile, j, k);
                checkPlayerNull(j, k, tile);

            }
        }
    }

    private void checkPlayerNull(int j, int k, ImageView tile) {
//        Player aPlayer = null;
//        //players
//        for (Player player : level.getPlayerData()) {
//            if (player.getPlayerCordX() == j && player.getPlayerCordY() == k) {
//                aPlayer = player;
//                break;
//            }
//        }

        if (level.getBoardData().getPlayerFromBoard(j, k) != null) {
            ImageView playerIv = new ImageView("/resources/playerImg.png");
            //sets tiles to specified size
            playerIv.setFitHeight(size);
            playerIv.setFitWidth(size);
            StackPane pane = new StackPane();
            pane.getChildren().add(tile);
            pane.getChildren().add(playerIv);
            tileGrid.add(pane, j, k);

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
                tile.setRotate(level.getBoardData().getTileFromBoard(j, k).getOrientation());
                tileGrid.add(tile, j, k);
                checkPlayerNull(j, k, tile);
            }
        }

    }

    /**
     * the arrow is clicked
     */
    private void onClickArrow(int x, int y, Image arrow){
        refreshBoard();

        //  For debugging
        System.out.println(x + "," + y);

        level.setTempX(x);
        level.setTempY(y);
        gameFlow.flow();
        tileGrid.getChildren().removeAll();
        //  Removes every thing on the screen


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
                        level.setTempCardinal(Board.Cardinals.RIGHT);
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
                        level.setTempCardinal(Board.Cardinals.LEFT);
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
                        level.setTempCardinal(Board.Cardinals.TOP);
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
                        level.setTempCardinal(Board.Cardinals.BOTTOM);
                        event.consume();
                    });
                }
            }
        }
    }
}

