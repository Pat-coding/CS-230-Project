package frontend.controllers;

import Tiles.FloorTile;
import backend.*;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    int size = 100;
    Image arrowDown = new Image(getClass().getResourceAsStream("/resources/arrowDOWN.png"));
    Image arrowUp = new Image(getClass().getResourceAsStream("/resources/arrowUP.png"));
    Image arrowLeft = new Image(getClass().getResourceAsStream("/resources/arrowLeft.png"));
    Image arrowRight = new Image(getClass().getResourceAsStream("/resources/arrowRight.png"));

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
    @FXML private StackPane invPane;
    @FXML private Button rotateLeft;
    @FXML private Button rotateRight;
    @FXML private Pane handTile;
    @FXML private Button moveButton;
    @FXML private Label playerTurn;

    private Level level;
    private int playerCounter = 0;

    private GameFlow gameFlow;
    private EventHandler<KeyEvent> keyListener = event -> {
        if (event.getCode() == KeyCode.UP) {
            level.pressUpFlag = true;
            System.out.println("UP");
            gameFlow.movePlayerOnBoard();
            refreshBoard();
            if (level.playerHasMovedFlag) {
                moveButton.setVisible(false);
            }
        } else if (event.getCode() == KeyCode.DOWN) {
            level.pressDownFlag = true;
            System.out.println("DOWN");
            gameFlow.movePlayerOnBoard();
            refreshBoard();
            if (level.playerHasMovedFlag) {
                moveButton.setVisible(false);
            }
        } else if (event.getCode() == KeyCode.LEFT) {
            level.pressLeftFlag = true;
            System.out.println("LEFT");
            gameFlow.movePlayerOnBoard();
            refreshBoard();
            if (level.playerHasMovedFlag) {
                moveButton.setVisible(false);
            }
        } else if (event.getCode() == KeyCode.RIGHT) {
            level.pressRightFlag = true;
            System.out.println("RIGHT");
            gameFlow.movePlayerOnBoard();
            refreshBoard();
            if (level.playerHasMovedFlag) {
                moveButton.setVisible(false);
            }
        }
        gameFlow.flow();
        event.consume();
    };

    public BoardController(Level level) {
        this.level = level;
        //  Provides gameFlow with the level information as well as the information regarding the who's turn it is.
        this.gameFlow = new GameFlow(this.level, Level.getPlayerIndex());
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
            gameFlow.flow();
            floorTileCommands();
            refreshBoard();
            displayInventory();
            caseActionTileDrawn();
            drawTileBtn.setVisible(false);
            event.consume();
        });
        endTurnBtn.setOnAction(event -> {
            gameFlow.flow();
            level.endTurnFlag = true;
            level.playerHasMovedFlag = false;
            gameFlow.flow();
            unHideArrows();
            drawTileBtn.setVisible(true);
            moveButton.setVisible(false);
            invPane.getChildren().clear();
            displayInventory();
            updatePlayerTurn();
            event.consume();
        });

        moveButton.setOnAction(event -> moveButton.setOnKeyPressed(keyListener));

        setupBoard();
        setupArrows();
        displayInventory();
        moveButton.setVisible(false);
        rotateLeft.setVisible(false);
        rotateRight.setVisible(false);
    }

    public void floorTileCommands() {
        if (level.playerHandFlag) {
            endTurnBtn.setVisible(false);
            System.out.println("this is the boardcontroller player index: " + Level.getPlayerIndex());
            FloorTile tileInHand = level.getPlayerData()[Level.getPlayerIndex()].getTileHand();
            rotateRight.setVisible(true);
            rotateLeft.setVisible(true);
            ImageView tile = new ImageView("resources/" + tileInHand.getType() + ".png");
            tile.setFitHeight(size);
            tile.setFitWidth(size);
            handTile.getChildren().add(tile);
            handTile.setVisible(true);
            rotateLeft.setOnAction(event -> {
                tileInHand.setOrientation(tileInHand.getOrientation() + 90);
                tile.setRotate(tileInHand.getOrientation());
                tile.setFitHeight(size);
                tile.setFitWidth(size);
                handTile.getChildren().clear();
                handTile.getChildren().add(tile);
            });

            rotateRight.setOnAction(event -> {
                tileInHand.setOrientation(tileInHand.getOrientation() - 90);
                tile.setRotate(tileInHand.getOrientation());
                tile.setFitHeight(size);
                tile.setFitWidth(size);
                handTile.getChildren().clear();
                handTile.getChildren().add(tile);
            });
        }
    }

    public void caseActionTileDrawn() {
        FloorTile tilePresent = level.getPlayerData()[Level.getPlayerIndex()].getTileHand();
        if (tilePresent == null) {
            moveButton.setVisible(true);
            hideArrows();
        }
    }

    private void displayInventory() {
        Player players = level.getPlayerData()[Level.getPlayerIndex()];
        for (int i = 0; i < players.getPlayerInventory().size(); i++) {
            System.out.println("resources/" + players.getPlayerInventory().get(i).getType() + ".png");
            ImageView inv = new ImageView("resources/" + players.getPlayerInventory().get(i).getType() + ".png");
            inv.setFitHeight(size / 2);
            inv.setFitHeight(size / 2);
            inv.setTranslateX(i * size / 1.5);
            invPane.getChildren().add(inv);
        }
    }

    private void setupBoard() {
        playerCounter = 0;
        Board board = level.getBoardData();
        for (int x = 0; x < board.getRowSize(); x++) {
            for (int y = 0; y < board.getColumnSize(); y++) {
                if (level.getBoardData().getTileFromBoard(x, y).isFixed()) {
                    ImageView tileFixed = new ImageView("resources/" + board.getTileFromBoard(x, y).getType() + "_fixed.png");
                    setTiles(tileFixed, x, y);
                } else {
                    System.out.println(level.getBoardData().getTileFromBoard(x, y).getOrientation());
                    ImageView tile = new ImageView("resources/" + board.getTileFromBoard(x, y).getType() + ".png");
                    setTiles(tile, x, y);

                }
            }
        }
        updatePlayerTurn();
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
            ImageView playerIv = new ImageView("/resources/Player" + playerCounter + ".png");
            //sets tiles to specified size
            playerIv.setFitHeight(size / 2);
            playerIv.setFitWidth(size / 2);
            StackPane pane = new StackPane();
            pane.getChildren().add(tile);
            pane.getChildren().add(playerIv);
            tileGrid.add(pane, j, k);
            playerCounter++;
            System.out.println(playerCounter);
        }
    }


    public void refreshBoard() {
        playerCounter = 0;
        tileGrid.getChildren().clear();
        for (int x = 0; x < level.getBoardData().getColumnSize(); x++) {
            for (int y = 0; y < level.getBoardData().getRowSize(); y++) {
                if (level.getBoardData().getTileFromBoard(x, y).isFixed()) {
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
        updatePlayerTurn();
    }

    public void hideArrows() {
        for (int i = 1; i < level.getBoardData().getRowSize() + 1; i++) {
            if (level.getTempCardinal() == null || level.getPlayerData()[Level.getPlayerIndex()] == null) {
                rightGrid.getChildren().get(i).setVisible(false);
                leftGrid.getChildren().get(i).setVisible(false);
                topGrid.getChildren().get(i).setVisible(false);
                bottomGrid.getChildren().get(i).setVisible(false);
            }
        }
        slottedUnset();
    }

    public void slottedUnset() {
        rotateRight.setVisible(false);
        rotateLeft.setVisible(false);
        handTile.setVisible(false);
        level.getPlayerData()[Level.getPlayerIndex()].setTileHand(null);
        level.playerHandFlag = false;
        endTurnBtn.setVisible(true);
    }

    public void unHideArrows() {
        for (int x = 0; x < level.getBoardData().getRowSize(); x++) {
            if (level.getBoardData().checkTileInsertionCol(x)) {
                bottomGrid.getChildren().get(x + 1).setVisible(true);
                topGrid.getChildren().get(x + 1).setVisible(true);
            }
        }
        for (int y = 0; y < level.getBoardData().getColumnSize(); y++) {
            if (level.getBoardData().checkTileInsertionRow(y)) {
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
                        moveButton.setVisible(true);
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
                        moveButton.setVisible(true);
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
                        moveButton.setVisible(true);
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
                        moveButton.setVisible(true);
                        event.consume();
                    });
                }
            }
        }
    }

    private void updatePlayerTurn(){
        playerTurn.setText("Player " + level.getPlayerData()[level.getPlayerIndex()].getProfile().getProfileName() + "'s turn!");
    }
}

