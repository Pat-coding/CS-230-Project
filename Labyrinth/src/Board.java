/**
 * @author Pat, Ash
 * @version 1.0
 */
import java.util.ArrayList;


public class Board {

    private String nameOfBoard;
    private final int rowSize;
    private final int columnSize;
    private Tile[][] tileCoordinates;
    private Player[][] playerCoordinates;

    public enum Cardinals { //add to the Floor Tile class or something
        TOP,
        BOTTOM,
        LEFT,
        RIGHT }

    //  Tile (Type, Orientation, State, fixed)
    public Board(String nameOfBoard, int[] sizeOfBoard) {
        rowSize = sizeOfBoard[0];
        columnSize = sizeOfBoard[1];
        this.setNameOfBoard(nameOfBoard);
        tileCoordinates = new Tile[getColumnSize()][getRowSize()];
        playerCoordinates = new Player[getColumnSize()][getRowSize()];
    }

    //new level format
    public Board(int[] sizeOfBoard, String nameOfBoard) {
        rowSize = sizeOfBoard[0];
        columnSize = sizeOfBoard[1];
        this.setNameOfBoard(nameOfBoard);
    }

    private String getNameOfBoard() {
        return nameOfBoard;
    }

    private void setNameOfBoard(String nameOfBoard) {
        this.nameOfBoard = nameOfBoard;
    }

    private void insertTile(int x, int y, Tile tile) {
        tileCoordinates[x][y] = tile;
    }

    private void insertPlayer(int x, int y, Player player) {
        playerCoordinates[x][y] = player;
    }

    private Tile getTileFromBoard(int x, int y) {
        return tileCoordinates[x][y];
    }

    private Player getPlayerFromBoard(int x, int y) {
        return playerCoordinates[x][y];
    }

    private int getRowSize() {
        return rowSize;
    }

    private int getColumnSize() {
        return columnSize;
    }

    /**
     * This Method check to see if any Fixed Tiles are present inside of a particular row of the board.
     *
     * @param y the row in question
     * @return Boolean result
     */
    public boolean checkTileInsertionRow(int y) {
        for (int x = 0; x < getRowSize(); x++) {
            if (getTileFromBoard(x, y).isFixed || getTileFromBoard(x, y).isFrozen) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will check to see if any fixed tiles are present of a particular column of the board.
     *
     * @param x the column in question
     * @return Boolean result
     */
    private boolean checkTilePlacementCol(int x) {
        for (int y = 0; y < getColumnSize(); y++) {
            if (getPlayerFromBoard(x, y).fixed || getPlayerFromBoard(x, y).frozen) {
                return false;
            }
        }
        return true;
    }

    //need to change, negative index
    public void setTilesFrozen(int x, int y) {
        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y + 1).frozen == true;
        }

        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y).frozen == true;
        }

        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y - 1).frozen == true;
        }
    }

    //need to change, negative index
    public void setTilesOnFire(int x, int y) {
        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y + 1).onFire == true;
        }

        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y).onFire == true;
        }

        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y - 1).onFire == true;
        }
    }

    //for loop, pushing the x or y values back? depending on which cardinal direction
    public void placeOnNewTile(Cardinals c, int x, int y, Tile tile) { //use enum for access cardinals on tiles
        if (c == Cardinals.TOP) {//shift index down from the second last (animations)
            discardTileToSilkBag(getTileFromBoard(x, getColumnSize()));
            for (int col = getColumnSize(); col >= 0; col--) {
                insertTile(x, col + 1, getTileFromBoard(x, col));
            }
            insertTile(x, y, tile);
        }

        if (c == Cardinals.BOTTOM) {
            discardTileToSilkBag(getTileFromBoard(x, getColumnSize()));
            for (int col = 0; col < getColumnSize(); col++) {
                insertTile(x, col - 1, getTileFromBoard(x, col));
            }
            insertTile(x, y, tile);
        }

        if (c == Cardinals.LEFT) {
            discardTileToSilkBag(getTileFromBoard(getRowSize(),y));
            for (int row = getRowSize(); row >= 0; row--) {
                insertTile(row + 1, getRowSize(), getTileFromBoard(row, y));
            }
            insertTile(x, y, tile);
        }

        if (c == Cardinals.RIGHT) {
            discardTileToSilkBag(getTileFromBoard(getRowSize(),y));
            for (int row = 0; row < getRowSize(); row++) {
                insertTile(row - 1, getRowSize(), getTileFromBoard(row, y));
            }
            insertTile(x, y, tile);
        }
    }

    public void movePlayerFromEndTile(int x, int y, Cardinals c) { //case move if end of tile, called from gameState?
        if (c == Cardinals.TOP) {
            insertPlayer(x, y, getPlayerFromBoard(x, getColumnSize()));
        }

        if (c == Cardinals.BOTTOM) {
            insertPlayer(x, y, getPlayerFromBoard(x, 0));
        }

        if (c == Cardinals.LEFT) {
            insertPlayer(x, y, getPlayerFromBoard(getRowSize(),y));
        }

        if (c == Cardinals.RIGHT) {
            insertPlayer(x, y, getPlayerFromBoard(0, getRowSize()));
        }
    }

    public Boolean checkPlayerEndTile(int x, int y) {
        return getPlayerFromBoard(x, y) != null;
    }

    //need to check how previous coordinates work again
    public void backTrackPlayer(ArrayList<Integer> tilesVisited, int x, int y) {
        if(getPlayerFromBoard(x, y).hasBeendoubled) { //should be tracker on last 2 turns
            insertPlayer(tilesVisited.get(4), tilesVisited.get(5), getPlayerFromBoard(x, y));
        } else {
            insertPlayer(tilesVisited.get(2), tilesVisited.get(3), getPlayerFromBoard(x, y));
        }
    }

    public void discardTileToSilkBag(Tile tile) {
        SilkBag.insertTileToBag(tile);
    }


}