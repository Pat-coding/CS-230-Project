import Tiles.FloorTile;
import Tiles.Tile;

import java.util.ArrayList;
/**
 * The board class structures the game board where the methods are operations that directly affects what is going on
 * the game board. This includes tiles and players on the game board.
 * @author Pat, Ash
 * @version 1.0
 */

public class Board {

    private final int rowSize;
    private final int columnSize;
    private String nameOfBoard;
    private FloorTile[][] tileCoordinates;
    private Player[][] playerCoordinates;
    private SilkBag bag;
    // needs a getter for bag
    public SilkBag getBag(){
        return bag;
    }

    //  Tile (Type, Orientation, State, fixed)
    /**
     * The constructor for a saved level format.
     * @param nameOfBoard The name of the board.
     * @param sizeOfBoard The size of the board.
     */
    public Board(String nameOfBoard, int[] sizeOfBoard) {
        rowSize = sizeOfBoard[0];
        columnSize = sizeOfBoard[1];
        this.setNameOfBoard(nameOfBoard);
        tileCoordinates = new FloorTile[getRowSize()][getColumnSize()];
        playerCoordinates = new Player[getRowSize()][getColumnSize()];
    }

    //new level format
    /**
     * The constructor for a new level format.
     * @param sizeOfBoard The name of the board.
     * @param nameOfBoard The size of the board.
     */
    private Board(int[] sizeOfBoard, String nameOfBoard) {
        rowSize = sizeOfBoard[0];
        columnSize = sizeOfBoard[1];
        this.setNameOfBoard(nameOfBoard);
    }

    /**
     * @return The name of the board.
     */
    public String getNameOfBoard() {
        return nameOfBoard;
    }



    /**
     * @param nameOfBoard The name of the board.
     */
    public void setNameOfBoard(String nameOfBoard) {
        this.nameOfBoard = nameOfBoard;
    }

    /**
     * @param x The x co-ordinate of the tile.
     * @param y The y co-ordinate of the tile.
     * @param tile THe tile at the position.
     */
    public void insertTile(int x, int y,FloorTile tile) {
        tileCoordinates[x][y] = tile;
    }

    /**
     *
     * @param x The x co-ordinate of the player.
     * @param y The y co-ordinate of the player.
     * @param player The player at the position.
     */
    private void insertPlayer(int x, int y, Player player) {
        playerCoordinates[x][y] = player;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public FloorTile getTileFromBoard(int x, int y) {
        return tileCoordinates[x][y];
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Player getPlayerFromBoard(int x, int y) {
        return playerCoordinates[x][y];
    }

    /**
     *
     * @return
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     *
     * @return
     */
    public int getColumnSize() {
        return columnSize;
    }

    /**
     * This Method check to see if any Fixed Tiles are present inside of a particular row of the board.
     * @param y the row in question.
     * @return Boolean result.
     */
    public boolean checkTileInsertionRow(int y) {
        for (int x = 0; x < getRowSize(); x++) {
            if (getTileFromBoard(x, y).isFixed()|| getTileFromBoard(x, y).getState().equals("FROZEN")) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will check to see if any fixed tiles are present of a particular column of the board.
     * @param x the column in question.
     * @return Boolean result.
     */
    private boolean checkTilePlacementCol(int x) {
        for (int y = 0; y < getColumnSize(); y++) {
            if (getTileFromBoard(x, y).isFixed() || getTileFromBoard(x, y).getState().equals("FROZEN")) {
                return false;
            }
        }
        return true;
    }

    /**
     *This method will set tiles to frozen state in a 3x3 area.
     * @param x The x co-ordinate of the center .
     * @param y The y co-ordinate of the center.
     */
    public void setTilesFrozen(int x, int y) {
        for (int row = x - 1; row < x + 3; row++) {
            if (getTileFromBoard(row, y + 1) != null) {
                getTileFromBoard(row, y + 1).setState("FROZEN");
            }
        }

        for (int row = x - 1; row < x + 3; row++) {
            if (getTileFromBoard(row, y) != null) {
                getTileFromBoard(row, y).setState("FROZEN");
            }
        }

        for (int row = x - 1; row < x + 3; row++) {
            if (getTileFromBoard(row, y - 1) != null) {
                getTileFromBoard(row, y - 1).setState("FROZEN");
            }
        }
    }

    /**
     *This method will set tiles on file in a 3x3 area.
     * @param x The x co-ordinate of the center .
     * @param y The y co-ordinate of the center.
     */
    public void setTilesOnFire(int x, int y) {
        for (int row = x - 1; row < x + 3; row++) {
            if (getTileFromBoard(row, y + 1) != null) {
                getTileFromBoard(row, y + 1).setState("FIRE");
            }
        }

        for (int row = x - 1; row < x + 3; row++) {
            if (getTileFromBoard(row, y) != null) {
                getTileFromBoard(row, y).setState("FIRE");
            }
        }

        for (int row = x - 1; row < x + 3; row++) {
            if (getTileFromBoard(row, y - 1) != null) {
                getTileFromBoard(row, y - 1).setState("FIRE");
            }
        }
    }

    /**
     * This method will shift tiles depending on their cardinal direction placement.
     * @param c    The cardinal direction placement.
     * @param x    The x co-ordinate where the player wants to slide tile in.
     * @param y    The y co-ordinate where the player wants to slide tile in.
     * @param tile The tile that is being slided in.
     */
    public FloorTile placeOnNewTile(Cardinals c, int x, int y,FloorTile tile) { //use enum for access cardinals on tiles
        if (c == Cardinals.TOP) {//shift index down from the second last (animations)
            FloorTile discardedTile = getTileFromBoard(x, getColumnSize());
            for (int col = getColumnSize() - 1; col >= 0; col--) {
                insertTile(x, col, getTileFromBoard(x, col - 1));
            }
            insertTile(x, y, tile);
            return discardedTile;
        }

        if (c == Cardinals.BOTTOM) {
            FloorTile discardedTile = getTileFromBoard(x, 0);
            for (int col = 0; col < getColumnSize(); col++) {
                insertTile(x, col, getTileFromBoard(x, col + 1));
            }
            insertTile(x, y, tile);
            return discardedTile;
        }

        if (c == Cardinals.LEFT) {
            FloorTile discardedTile = getTileFromBoard(getRowSize(), y);
            for (int row = getRowSize(); row > 0; row--) {
                insertTile(row, getRowSize(), getTileFromBoard(row - 1, y));
            }
            insertTile(x, y, tile);
            return discardedTile;
        }

        if (c == Cardinals.RIGHT) {
            FloorTile discardedTile = getTileFromBoard(0, y);
            for (int row = 0; row < getRowSize(); row++) {
                insertTile(row, getRowSize(), getTileFromBoard(row + 1, y));
            }
            insertTile(x, y, tile);
            return discardedTile;
        }
        return null;
    }

    /**
     * This method will move player when their player piece is at the end of the tile placement.
     * @param x The x co-ordinate of the new position of the player.
     * @param y The y co-ordinate of the new position of the player.
     * @param c The cardinal place of the tile insertion.
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
     * This method checks to see if their is a player at the end of the tile.
     * @param x The x co-ordinate of the tile on the end.
     * @param y The y co-ordinate of the tile on the end.
     * @return The Boolean value true or false.
     */
    public Boolean checkPlayerEndTile(int x, int y) {
        return getPlayerFromBoard(x, y) != null;
    }

    //TODO redo using nested for loop as this is inefficient
    /**
     *This method searches and store player information.
     * @param x The starting x co-ordinate of the search.
     * @param y The starting y co-ordinate of the search.
     * @param player The player in search .
     * @return The x and y co-ordinate in as an int array.
     */
    public int[] playerLocationOnBoard(int x, int y, Player player) {
        int[] coords = new int[2];
        if (x == getRowSize() && y == getColumnSize()) {
            return null;
        } else {
            if (getPlayerFromBoard(x, y) == player) {
                coords[0] = x;
                coords[1] = y;
                return coords;
            } else if (x < (getColumnSize() - 1)) {
                playerLocationOnBoard(x, y + 1, player);
            } else
                playerLocationOnBoard(x + 1, y, player);
        }
        return null;
    }

    /**
     * This method moves player to a new position.
     * @param newX The x co-ordinate of the new position.
     * @param newY The y co-ordinate of the new position.
     * @param oldX The x co-ordinate of the old position.
     * @param oldY The x co-ordinate of the old position.
     */
    public void movePlayer(int newX, int newY, int oldX, int oldY) {
        insertPlayer(newX, newY, getPlayerFromBoard(oldX, oldY));
        insertPlayer(oldX, oldY, null);
    }

    /**
     * This method will set the player back to their previous co-ordinate.
     * @param tilesVisited The player's previous co-ordinates history.
     * @param x The new x co-ordinate of the player.
     * @param y The new y co-ordinate of the player.
     */
    public void backTrackPlayer(ArrayList<Integer> tilesVisited, int x, int y) { //TODO check index of tilesVisited
        if ((getTileFromBoard(tilesVisited.get(4), tilesVisited.get(5))).getState().equals("FIRE") ||
                (getTileFromBoard(tilesVisited.get(2), tilesVisited.get(3))).getState().equals(("FIRE"))) {
            //error message here stating player cannot go back because tile is on fire
        } else {
                movePlayer(tilesVisited.get(2), tilesVisited.get(3), x, y);
        }
    }

    /**
     * This method will search and store the goal co-ordinate on the board.
     * @return
     */
    public int[] getGoal() {
        int[] coords = new int[2];
        for (int x = 0; x < getRowSize(); x++) {
            for ( int y = 0; y < getColumnSize(); y++) {
                if (getTileFromBoard(x,y) == getTileFromBoard(x,y)) { //TODO will change to the goal class
                    coords[0] = x;
                    coords[1] = y;
                    return coords;
                }
            }
        }
        return null;
    }

//    /**
//     * This method will discard tiles from the board to the SilkBag.
//     * @param tile The tile being discarded.
//     */
//    public void discardTileToSilkBag(String tile) {
//        SilkBag.insertTileToBag(tile);
//    }

    public enum Cardinals { //move to the Floor Tile class
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }


}