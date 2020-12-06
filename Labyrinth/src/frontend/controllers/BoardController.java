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

/**
 * BoardController setup the entire game for the user to play on and allows for the user
 * interaction to occur on the board.
 *
 * @author Deniz Oral
 */
public class BoardController implements Initializable {

    private static final int SIZE = 100;
    private static final double TILE_SCALING = 2.0;
    private static final double TILE_LOCATION_SCALING = 2.0;
    private static final int ROTATION = 90;
    private static final int EXIT_GAME = 404;
    private static final int DEFAULT_PLAYER_INDEX = 0;

    private Image arrowDown =
            new Image(getClass().getResourceAsStream("/resources/arrowDOWN.png"));
    private Image arrowUp =
            new Image(getClass().getResourceAsStream("/resources/arrowUP.png"));
    private Image arrowLeft =
            new Image(getClass().getResourceAsStream("/resources/arrowLeft.png"));
    private Image arrowRight =
            new Image(getClass().getResourceAsStream("/resources/arrowRight.png"));

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
    private Pane pane;
    @FXML
    private HBox inventory;
    @FXML
    private StackPane invPane;
    @FXML
    private Button rotateLeft;
    @FXML
    private Button rotateRight;
    @FXML
    private Pane handTile;
    @FXML
    private Button moveButton;
    @FXML
    private Label playerTurn;
    @FXML
    private Pane playerColour;

    private Level level;

    private GameFlow gameFlow;

    /**
     * Variable for key events when moving players.
     */
    private EventHandler<KeyEvent> keyListener = event -> {
        if (event.getCode() == KeyCode.UP) {
            level.pressUpFlag = true;
            System.out.println("UP");
            gameFlow.movePlayerOnBoard();

        } else if (event.getCode() == KeyCode.DOWN) {
            level.pressDownFlag = true;
            System.out.println("DOWN");
            gameFlow.movePlayerOnBoard();

        } else if (event.getCode() == KeyCode.LEFT) {
            level.pressLeftFlag = true;
            System.out.println("LEFT");
            gameFlow.movePlayerOnBoard();

        } else if (event.getCode() == KeyCode.RIGHT) {
            level.pressRightFlag = true;
            System.out.println("RIGHT");
            gameFlow.movePlayerOnBoard();
        }
        refreshBoard();
        gameFlow.flow();
        event.consume();
    };

    /**
     * Constructor for the BoardController.
     *
     * @param level Pass save game data to the board controller.
     */
    public BoardController(Level level) {
        this.level = level;
        Level.setPlayerIndex(setPlayerIndex());

        this.gameFlow = new GameFlow(this.level, Level.getPlayerIndex());
    }


    /**
     * Initialize the start of the setup.
     *
     * @param location  The URL of the resource bundle.
     * @param resources The resources of the resource bundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveGameBtn.setOnAction(event -> {
            level.saveButtonFlag = true;
            gameFlow.flow();
        });
        quitBtn.setOnAction(event -> {
            System.exit(EXIT_GAME);
        });
        drawTileBtn.setOnAction(event -> {
            this.level.drawTileFlag = true;
            level.playerHasMovedFlag = false;
            gameFlow.flow();
            floorTileCommands();
            displayInventory();
            caseActionTileDrawn();
            drawTileBtn.setVisible(false);
            event.consume();
        });
        endTurnBtn.setOnAction(event -> {
            level.endTurnFlag = true;
            level.playerHasMovedFlag = false;
            gameFlow.flow();
            unHideArrows();
            drawTileBtn.setVisible(true);
            moveButton.setVisible(false);
            invPane.getChildren().clear();
            displayInventory();
            updatePlayerTurn();
            setPlayerColour();
            event.consume();
        });

        moveButton.setOnAction(event -> moveButton.setOnKeyPressed(keyListener));
        setPlayerColour();
        setupBoard();
        setupArrows();
        displayInventory();
        moveButton.setVisible(false);
        rotateLeft.setVisible(false);
        rotateRight.setVisible(false);
    }

    /**
     * Rotates the floor tile displayed on screen.
     */
    private void floorTileCommands() {
        if (level.playerHandFlag) {
            endTurnBtn.setVisible(false);
            FloorTile tileInHand = level.getPlayerData()[Level.getPlayerIndex()].getTileHand();
            rotateRight.setVisible(true);
            rotateLeft.setVisible(true);
            ImageView tile = new ImageView("resources/" + tileInHand.getType() + ".png");
            tile.setFitHeight(SIZE);
            tile.setFitWidth(SIZE);
            handTile.getChildren().add(tile);
            handTile.setVisible(true);
            rotateLeft.setOnAction(event -> {
                tileInHand.setOrientation(tileInHand.getOrientation() + ROTATION);
                tile.setRotate(tileInHand.getOrientation());
                tile.setFitHeight(SIZE);
                tile.setFitWidth(SIZE);
                handTile.getChildren().clear();
                handTile.getChildren().add(tile);
            });

            rotateRight.setOnAction(event -> {
                tileInHand.setOrientation(tileInHand.getOrientation() - ROTATION);
                tile.setRotate(tileInHand.getOrientation());
                tile.setFitHeight(SIZE);
                tile.setFitWidth(SIZE);
                handTile.getChildren().clear();
                handTile.getChildren().add(tile);
            });
        }
    }

    /**
     * set the player index at the start of the game.
     *
     * @return The player that is starting the game.
     */
    private int setPlayerIndex() {
        for (int i = 0; i < level.getPlayerData().length; i++) {
            if (level.getPlayerData()[i].getPlayerTurn()) {
                return i;
            }
        }
        return DEFAULT_PLAYER_INDEX;
    }

    /**
     * Hide board arrows when action tiles are drawn from the silk bag.
     */
    private void caseActionTileDrawn() {
        FloorTile tilePresent = level.getPlayerData()[Level.getPlayerIndex()].getTileHand();
        if (tilePresent == null) {
            moveButton.setVisible(true);
            hideArrows();
        }
    }

    /**
     * Display the inventory of the players.
     */
    private void displayInventory() {
        Player players = level.getPlayerData()[Level.getPlayerIndex()];
        for (int i = 0; i < players.getPlayerInventory().size(); i++) {
            System.out.println("resources/" + players.getPlayerInventory().get(i).getType() + ".png");
            ImageView inv = new ImageView("resources/" + players.getPlayerInventory().get(i).getType() + ".png");
            inv.setFitHeight(SIZE / TILE_SCALING);
            inv.setFitHeight(SIZE / TILE_SCALING);
            inv.setTranslateX(i * SIZE / TILE_LOCATION_SCALING);
            invPane.getChildren().add(inv);
        }
    }

    /**
     * Setup board on initialization.
     */
    private void setupBoard() {
        Board board = level.getBoardData();
        for (int x = 0; x < board.getRowSize(); x++) {
            for (int y = 0; y < board.getColumnSize(); y++) {
                if (level.getBoardData().getTileFromBoard(x, y).isFixed()) {
                    ImageView tileFixed = new ImageView("resources/" + board.getTileFromBoard(x, y).getType() + "_fixed.png");
                    setTiles(tileFixed, x, y);
                } else {
                    ImageView tile = new ImageView("resources/" + board.getTileFromBoard(x, y).getType() + ".png");
                    setTiles(tile, x, y);

                }
            }
        }
        updatePlayerTurn();
    }

    /**
     * Set Images to tiles on the board.
     *
     * @param tile The image being set to the tile.
     * @param x    The x co-ordinate of the board.
     * @param y    The y co-ordinate of the board.
     */
    private void setTiles(ImageView tile, int x, int y) {
        tile.setFitHeight(SIZE);
        tile.setFitWidth(SIZE);
        tile.setRotate(level.getBoardData().getTileFromBoard(x, y).getOrientation());
        tileGrid.add(tile, x, y);
        checkPlayerNull(x, y, tile);

    }

    /**
     * Display player image on game as an indicator.
     */
    private void setPlayerColour() {
        ImageView playerIv = new ImageView("/resources/Player" + Level.getPlayerIndex() + ".png");
        playerIv.setFitHeight(SIZE / TILE_SCALING);
        playerIv.setFitWidth(SIZE / TILE_SCALING);
        playerColour.getChildren().add(playerIv);

    }

    /**
     * Set player image to board when there is a player on the board.
     *
     * @param j    The x co-ordinate of the player.
     * @param k    The y co-ordinate of the player.
     * @param tile The tile where the player is on board.
     */
    private void checkPlayerNull(int j, int k, ImageView tile) {

        if (level.getBoardData().getPlayerFromBoard(j, k) != null) {
            for (int i = 0; i < level.getPlayerData().length; i++) {
                if (level.getPlayerData()[i].equals(level.getBoardData().getPlayerFromBoard(j, k))) {
                    ImageView playerIv = new ImageView("/resources/Player" + i + ".png");
                    //sets tiles to specified size
                    playerIv.setFitHeight(SIZE / TILE_SCALING);
                    playerIv.setFitWidth(SIZE / TILE_SCALING);
                    StackPane pane = new StackPane();
                    pane.getChildren().add(tile);
                    pane.getChildren().add(playerIv);
                    tileGrid.add(pane, j, k);
                }
            }

        }
    }

    /**
     * Refresh the current tile images on board with newly located tiles.
     */
    private void refreshBoard() {
        tileGrid.getChildren().clear();
        for (int x = 0; x < level.getBoardData().getColumnSize(); x++) {
            for (int y = 0; y < level.getBoardData().getRowSize(); y++) {
                if (level.getBoardData().getTileFromBoard(x, y).isFixed()) {
                    ImageView tileFixed = new ImageView("resources/" + level.getBoardData().getTileFromBoard(x, y).getType() + "_fixed.png");
                    setTiles(tileFixed, x, y);
                } else {
                    ImageView tile = new ImageView("resources/" + level.getBoardData().getTileFromBoard(x, y).getType() + ".png");
                    setTiles(tile, x, y);
                }
            }
        }
        updatePlayerTurn();
    }

    /**
     * Make the arrows around the board hidden.
     */
    private void hideArrows() {
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

    /**
     * Make the rotation and tile hand being displayed hidden are the tile has been slotted into the board.
     */
    private void slottedUnset() {
        rotateRight.setVisible(false);
        rotateLeft.setVisible(false);
        handTile.setVisible(false);
        level.getPlayerData()[Level.getPlayerIndex()].setTileHand(null);
        level.playerHandFlag = false;
        endTurnBtn.setVisible(true);
    }

    /**
     * Make the arrows visible again after the tile has been drawn.
     */
    private void unHideArrows() {
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

    /**
     * Setup arrows around the board during the initialization phase.
     */
    private void setupArrows() {
        topGrid.setTranslateX(SIZE);
        bottomGrid.setTranslateX(SIZE);
        for (int x = 0; x < level.getBoardData().getRowSize(); x++) {
            for (int y = 0; y < level.getBoardData().getColumnSize(); y++) {
                if (x == level.getBoardData().getRowSize() - 1) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(SIZE);
                    tileImg.setFitWidth(SIZE);
                    tileImg.setImage(arrowLeft);
                    rightGrid.add(tileImg, x, y);

                    final int xx = x;
                    final int yy = y;

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
                    tileImg.setFitHeight(SIZE);
                    tileImg.setFitWidth(SIZE);
                    tileImg.setImage(arrowRight);
                    leftGrid.add(tileImg, x, y);

                    final int xx = x;
                    final int yy = y;
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
                    tileImg.setFitHeight(SIZE);
                    tileImg.setFitWidth(SIZE);
                    tileImg.setImage(arrowDown);
                    topGrid.add(tileImg, x, y);

                    final int xx = x;
                    final int yy = y;

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

                } else if (y == level.getBoardData().getColumnSize() - 1) {
                    ImageView tileImg = new ImageView();
                    tileImg.setFitHeight(SIZE);
                    tileImg.setFitWidth(SIZE);
                    tileImg.setImage(arrowUp);
                    bottomGrid.add(tileImg, x, y);

                    final int xx = x;
                    final int yy = y;
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

    /**
     * Text showing which player it is as an indicator.
     */
    private void updatePlayerTurn() {
        playerTurn.setText(level.getPlayerData()[Level.getPlayerIndex()].getProfile().getProfileName() + "'s turn!");
    }
}

