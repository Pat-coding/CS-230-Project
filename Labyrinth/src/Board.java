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

    public enum Cardinals { //move to the Floor Tile class
        TOP,
        BOTTOM,
        LEFT,
        RIGHT }

    //  Tile (Type, Orientation, State, fixed)
    public Board(String nameOfBoard, int[] sizeOfBoard) {
        rowSize = sizeOfBoard[0];
        columnSize = sizeOfBoard[1];
        this.setNameOfBoard(nameOfBoard);
        tileCoordinates = new Tile[getRowSize()][getColumnSize()];
        playerCoordinates = new Player[getRowSize()][getColumnSize()];
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

    public void insertTile(int x, int y, Tile tile) {
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
     * @param y the row in question
     * @return Boolean result
     */
    public boolean checkTileInsertionRow(int y) {
        for (int x = 0; x < getRowSize(); x++) {
            if (getTileFromBoard(x, y).fixed || getTileFromBoard(x, y).frozen) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will check to see if any fixed tiles are present of a particular column of the board.
     * @param x the column in question
     * @return Boolean result
     */
    private boolean checkTilePlacementCol(int x) {
        for (int y = 0; y < getColumnSize(); y++) {
            if (getTileFromBoard(x, y).fixed || getTileFromBoard(x, y).frozen) {
                return false;
            }
        }
        return true;
    }

    //TODO need to change, out of bounds unaccountable
    public void setTilesFrozen(int x, int y) {
        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y + 1).frozen = true;
        }

        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y).frozen = true;
        }

        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y - 1).frozen = true;
        }
    }

    //TODO need to change, out of bounds unaccountable
    public void setTilesOnFire(int x, int y) {
        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y + 1).onFire = true;
        }

        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y).onFire = true;
        }

        for (int row = x - 1; row < x + 3; row++) {
            getTileFromBoard(row, y - 1).onFire = true;
        }
    }

    /**
     *This method will shift tiles depending on their cardinal direction placement
     * @param c The cardinal direction placement
     * @param x The x co-ordinate where the player wants to slide tile in
     * @param y The y co-ordinate where the player wants to slide tile in
     * @param tile The tile that is being slided in
     */
    public void placeOnNewTile(Cardinals c, int x, int y, Tile tile) { //use enum for access cardinals on tiles
        if (c == Cardinals.TOP) {//shift index down from the second last (animations)
            discardTileToSilkBag(getTileFromBoard(x, getColumnSize()));
            for (int col = getColumnSize(); col >= 0; col--) {
                insertTile(x, col, getTileFromBoard(x, col));
            }
            insertTile(x, y, tile);
        }

        if (c == Cardinals.BOTTOM) {
            discardTileToSilkBag(getTileFromBoard(x, getColumnSize()));
            for (int col = 0; col < getColumnSize(); col++) {
                insertTile(x, col , getTileFromBoard(x, col));
            }
            insertTile(x, y, tile);
        }

        if (c == Cardinals.LEFT) {
            discardTileToSilkBag(getTileFromBoard(getRowSize(),y));
            for (int row = getRowSize(); row >= 0; row--) {
                insertTile(row, getRowSize(), getTileFromBoard(row, y));
            }
            insertTile(x, y, tile);
        }

        if (c == Cardinals.RIGHT) {
            discardTileToSilkBag(getTileFromBoard(getRowSize(),y));
            for (int row = 0; row < getRowSize(); row++) {
                insertTile(row, getRowSize(), getTileFromBoard(row, y));
            }
            insertTile(x, y, tile);
        }
    }

    /**
     *This method will move player when their player piece is at the end of the tile placement
     * @param x The x co-ordinate of the new position of the player
     * @param y The y co-ordinate of the new position of the player
     * @param c The cardinal place of the tile insertion
     */
    public void movePlayerFromEndTile(int x, int y, Cardinals c) { //case move if end of tile, called from gameState?
        if (c == Cardinals.TOP) {
            movePlayer(x, y, x, getColumnSize());
        }

        if (c == Cardinals.BOTTOM) {
            movePlayer(x, y, x, 0);
        }

        if (c == Cardinals.LEFT) {
            movePlayer(x, y, getRowSize(), y);
        }

        if (c == Cardinals.RIGHT) {
            movePlayer(x, y, 0, y);
        }
    }

    /**
     *This method checks to see if their is a player at the end of the tile
     * @param x The x co-ordinate of the tile on the end
     * @param y The y co-ordinate of the tile on the end
     * @return The Boolean value true or false
     */
    public Boolean checkPlayerEndTile(int x, int y) {
        return getPlayerFromBoard(x, y) != null;
    }

    /**
     *This method moves player to a new position
     * @param newX The x co-ordinate of the new position
     * @param newY The y co-ordinate of the new position
     * @param oldX The x co-ordinate of the old position
     * @param oldY The x co-ordinate of the old position
     */

    public void movePlayer(int newX, int newY, int oldX, int oldY) {
        insertPlayer(newX, newY,getPlayerFromBoard(oldX, oldY));
        insertPlayer(oldX, oldY, null);
    }

    /**
     *This method will set the player back to their previous co-ordinate
     * @param tilesVisited
     * @param x
     * @param y
     */
    public void backTrackPlayer(ArrayList<Integer> tilesVisited, int x, int y) { //TODO need to check prev cords again
        if(getPlayerFromBoard(x, y).hasBeenDoubled) { //should be tracker on last 2 turns
            insertPlayer(tilesVisited.get(4), tilesVisited.get(5), getPlayerFromBoard(x, y));
        } else {
            insertPlayer(tilesVisited.get(2), tilesVisited.get(3), getPlayerFromBoard(x, y));
        }
    }

    /**
     *
     * @param tile
     */
    public void discardTileToSilkBag(Tile tile) {
        SilkBag.insertTileToBag(tile);
    }


}