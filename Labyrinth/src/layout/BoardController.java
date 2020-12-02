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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import java.util.ResourceBundle;

public class BoardController implements Initializable, KeyListener {

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
            changeSaveGameFlag();
        });

        quitBtn.setOnAction(event -> {
            System.exit(404);
        });

        drawTileBtn.setOnAction(event -> {
            level.drawTileFlag = true;
        });

        endTurnBtn.setOnAction(event -> {
            level.endTurnButton = true;
        });


    }

    private void changeSaveGameFlag() {
        level.saveButtonFlag = true;
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
        level.setTempX(x);
        level.setTempY(y);


        FloorTile newTile = (FloorTile)FileManager.createPlayerInventoryTiles("TShaped", 0); //TODO, replace it later
        level.getBoardData().placeOnNewTile(c, x, y, newTile);

        refreshBoard();
    }

    public boolean setupArrows() {
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


                        level.arrowFlagPressedVert = true;
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

                        level.arrowFlagPressedVert = true;
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

                        level.arrowFlagPressedHorz = true;

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

                        level.arrowFlagPressedHorz = true;

                        level.setTempCardinal(Board.Cardinals.BOTTOM);
                        event.consume();
                    });
                }
            }
            return false;
        }
        return false;
    }

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
            level.saveButtonFlag = true;
            //  Override previous save game
            if (!saveGameCheck()) {
                Level.getSavedLevels().add(this.level);
            }
        }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                Level.pressUpFlag = true;
                break;
            case KeyEvent.VK_DOWN:
                Level.pressDownFlag = true;
                break;
            case KeyEvent.VK_LEFT:
                Level.pressLeftFlag = true;
                break;
            case KeyEvent.VK_RIGHT :
                Level.pressRightFlag = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

