package frontend.controllers;

import backend.*;

import frontend.loaders.LeaderboardLoader;
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
import java.util.ArrayList;
import javafx.scene.input.KeyEvent;

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
    @FXML private Pane pane;
    @FXML private HBox inventory;
    @FXML private Pane invPane;
    private Level level;
    private int playerIndex;
    private GameFlow gameFlow;

    private EventHandler<KeyEvent> keyListener = event -> {
        if (event.getCode() == KeyCode.UP) {
            level.pressUpFlag = true;
            System.out.println("UP");
            gameFlow.movePlayerOnBoard();
            refreshBoard();
        } else if (event.getCode() == KeyCode.DOWN) {
            level.pressDownFlag = true;
            System.out.println("DOWN");
            gameFlow.movePlayerOnBoard();
            refreshBoard();
        } else if (event.getCode() == KeyCode.LEFT) {
            level.pressLeftFlag = true;
            System.out.println("LEFT");
            gameFlow.movePlayerOnBoard();
            refreshBoard();
        } else if (event.getCode() == KeyCode.RIGHT) {
            level.pressRightFlag = true;
            System.out.println("RIGHT");
            gameFlow.movePlayerOnBoard();
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
        this.playerIndex = setPlayerIndex();
        System.out.println("This is the player index " + this.playerIndex);
        //  Provides gameFlow with the level information as well as the information regarding the who's turn it is.
        this.gameFlow = new GameFlow(this.level, this.playerIndex);
    }

    public int setPlayerIndex() {
        for (int i = 0; i < level.getPlayerData().length; i++) {
            if (level.getPlayerData()[i].getPlayerTurn() == true) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveGameBtn.setOnAction(event -> {
            level.saveButtonFlag = true;
            gameFlow.flow();
        });
        quitBtn.setOnAction(event -> {
            System.exit(404);
        });
        drawTileBtn.setOnAction(event -> {
            this.level.drawTileFlag = true;
            level.playerHasMovedFlag = false;
            drawTileBtn.setOnKeyPressed(keyListener);
            gameFlow.flow();
            refreshBoard();
            setupInventory();
            event.consume();
        });
        endTurnBtn.setOnAction(event -> {
            level.endTurnFlag = true;
            level.playerHasMovedFlag = false;
            gameFlow.flow();
            unHideArrows();
            setupInventory();
            event.consume();
        });
        setupBoard();
        setupArrows();
        setupInventory();
    }

    private void setupInventory() {

        for (int i = 0; i < level.getPlayerData()[playerIndex].getPlayerInventory().size(); i++) {
            System.out.println("resources/" + level.getPlayerData()[playerIndex].getPlayerInventory().get(i).getType() + ".png");
            ImageView inv = new ImageView("resources/" + level.getPlayerData()[playerIndex].getPlayerInventory().get(i).getType() + ".png");
            inv.setFitHeight(size);
            inv.setFitHeight(size);
            invPane.getChildren().add(inv);
        }

    }


    private void setupBoard() {
        for (int x = 0; x < level.getBoardData().getRowSize(); x++) {
            for (int y = 0; y < level.getBoardData().getColumnSize(); y++) {
                if(level.getBoardData().getTileFromBoard(x, y).isFixed()) {
                    ImageView tileFixed = new ImageView("resources/" + level.getBoardData().getTileFromBoard(x, y).getType() + "_fixed.png");
                    setTiles(tileFixed, x, y);
                } else {
                    System.out.println(level.getBoardData().getTileFromBoard(x, y).getOrientation());
                    ImageView tile = new ImageView("resources/" + level.getBoardData().getTileFromBoard(x, y).getType() + ".png");
                    setTiles(tile, x, y);

                }
            }
        }
    }


    private void setTiles(ImageView tile, int x, int y) {
        tile.setFitHeight(size);
        tile.setFitWidth(size);
        tile.setRotate(level.getBoardData().getTileFromBoard(x, y).getOrientation());
        tileGrid.add(tile, x, y);
        checkPlayerNull(x, y, tile);

    }

    private void checkPlayerNull(int j, int k, ImageView tile) {
        if (level.getBoardData().getPlayerFromBoard(j, k) != null) {
            System.out.println("x: " + j + "y: " + k);
            ImageView playerIv = new ImageView("/resources/playerImg.png");
            //sets tiles to specified size
            playerIv.setFitHeight(size/2);
            playerIv.setFitWidth(size/ 2);
            StackPane pane = new StackPane();
            pane.getChildren().add(tile);
            pane.getChildren().add(playerIv);
            tileGrid.add(pane, j, k);
        }
    }


    public void refreshBoard() {
        tileGrid.getChildren().clear();
        for (int x = 0; x < level.getBoardData().getColumnSize(); x++) {
            for (int y = 0; y < level.getBoardData().getRowSize(); y++) {
                if(level.getBoardData().getTileFromBoard(x, y).isFixed()) {
                    System.out.println(level.getBoardData().getTileFromBoard(x, y).isFixed());
                    ImageView tileFixed = new ImageView("resources/" + level.getBoardData().getTileFromBoard(x, y).getType() + "_fixed.png");
                    setTiles(tileFixed, x, y);
                } else {
                    System.out.println(level.getBoardData().getTileFromBoard(x, y).isFixed());
                    ImageView tile = new ImageView("resources/" + level.getBoardData().getTileFromBoard(x, y).getType() + ".png");
                    setTiles(tile, x, y);
                }
            }
        }
    }

    public void hideArrows () {
        for (int i = 1; i < level.getBoardData().getRowSize() + 1; i++) {
            rightGrid.getChildren().get(i).setVisible(false);
            leftGrid.getChildren().get(i).setVisible(false);
            topGrid.getChildren().get(i).setVisible(false);
            bottomGrid.getChildren().get(i).setVisible(false);
        }

    }


    public void unHideArrows() {
        for (int x = 0; x < level.getBoardData().getRowSize(); x++) {
                if (level.getBoardData().checkTileInsertionCol(x)) {
                    bottomGrid.getChildren().get(x + 1).setVisible(true);
                    topGrid.getChildren().get(x + 1).setVisible(true);
                }
            }
            for (int y = 0; y < level.getBoardData().getColumnSize(); y++) {
                if(level.getBoardData().checkTileInsertionRow(y)) {
                    rightGrid.getChildren().get(y + 1).setVisible(true);
                    leftGrid.getChildren().get(y + 1).setVisible(true);
                }
            }
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

                    if (!level.getBoardData().checkTileInsertionRow(y)) {
                        rightGrid.getChildren().get(y + 1).setVisible(false);
                    }

                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

                        System.out.println("arrow facing left pressed ");
                        level.setTempCardinal(Board.Cardinals.RIGHT);
                        level.setTempX(xx);
                        level.setTempY(yy);
                        gameFlow.flow();
                        tileGrid.getChildren().clear();
                        refreshBoard();
                        hideArrows();
                        event.consume();
                    });

                } else if (x == 0) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowRight);
                    leftGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;
                    if (!level.getBoardData().checkTileInsertionRow(y)) {
                        leftGrid.getChildren().get(y + 1).setVisible(false);
                    }
                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

                        System.out.println("arrow facing right pressed");
                        level.setTempCardinal(Board.Cardinals.LEFT);
                        level.setTempX(xx);
                        level.setTempY(yy);
                        gameFlow.flow();
                        tileGrid.getChildren().clear();
                        refreshBoard();
                        hideArrows();
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

                    if (!level.getBoardData().checkTileInsertionCol(x)) {
                        topGrid.getChildren().get(x + 1).setVisible(false);
                    }
                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

                        System.out.println("arrow facing down pressed");
                        level.setTempCardinal(Board.Cardinals.TOP);
                        level.setTempX(xx);
                        level.setTempY(yy);
                        gameFlow.flow();
                        tileGrid.getChildren().clear();
                        refreshBoard();
                        hideArrows();
                        event.consume();
                    });

                } else if (y == level.getBoardData().getColumnSize() - 1) { //4 is the board size, we will get board size from save files, this is just for testing right now.
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setImage(arrowUp);
                    bottomGrid.add(tileImg, x, y);

                    final int xx = x, yy = y;
                    if (!level.getBoardData().checkTileInsertionCol(x)) {
                        bottomGrid.getChildren().get(x + 1).setVisible(false);
                    }
                    tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        System.out.println("arrow facing up pressed");
                        level.setTempCardinal(Board.Cardinals.BOTTOM);
                        level.setTempX(xx);
                        level.setTempY(yy);
                        gameFlow.flow();
                        tileGrid.getChildren().clear();
                        refreshBoard();
                        hideArrows();
                        event.consume();
                    });
                }
            }
        }
    }
}

