package backend;

import Tiles.FloorTile;
import Tiles.GoalTile;

import java.util.HashMap;


/**
 * The board class structures the game board where the methods are operations that directly affects what is going on
 * the game board. This includes tiles and players on the game board.
 *
 * @author Pat
 * @version 1.0
 */

public class Board {

    /**
     * The cardinal directions of the board
     */
    public enum Cardinals {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        NULL
    }

    private final int rowSize;
    private final int columnSize;
    private String[] profileNames;
    private String nameOfBoard;
    private FloorTile[][] tileCoordinates;
    private Player[][] playerCoordinates;
    private HashMap<FloorTile, Integer> statusTime = new HashMap<>();

    /**
     * Constructor for a saved level format.
     *
     * @param nameOfBoard The name of the board.
     * @param sizeOfBoard The size of the board.
     */
    public Board(String nameOfBoard, int[] sizeOfBoard, String[] profileNames) {
        this.rowSize = sizeOfBoard[0];
        this.columnSize = sizeOfBoard[1];
        this.setNameOfBoard(nameOfBoard);
        this.tileCoordinates = new FloorTile[getRowSize()][getColumnSize()];
        this.playerCoordinates = new Player[getRowSize()][getColumnSize()];
        this.profileNames = profileNames;
    }

    /**
     * Constructor for a new level format.
     *
     * @param sizeOfBoard The name of the board.
     * @param nameOfBoard The size of the board.
     */
    public Board(String nameOfBoard, int[] sizeOfBoard) {
        this.rowSize = sizeOfBoard[0];
        this.columnSize = sizeOfBoard[1];
        this.setNameOfBoard(nameOfBoard);
        this.tileCoordinates = new FloorTile[getRowSize()][getColumnSize()];
        this.playerCoordinates = new Player[getRowSize()][getColumnSize()];
    }

    /**
     * Gets the names of the profiles playing the current board.
     * @return as String[] names
     */

    public String[] getProfileNames() {
        return profileNames;
    }

    /**
     * Sets the name of the profiles playing the board
     * @param profileNames names of profile array
     */

    public void setProfileNames(String[] profileNames) {
        this.profileNames = profileNames;
    }

    /**
     * Method is used to retrieve the name of the board.
     * @return The name of the board.
     */
    public String getNameOfBoard() {
        return nameOfBoard;
    }

    /**
     * Method is used to set the name of the board.
     * @param nameOfBoard Name of Board.
     */
    public void setNameOfBoard(String nameOfBoard) {
        this.nameOfBoard = nameOfBoard;
    }



    /**
     * Returns the Size of the Row in the table.
     * @return Integer of size of row.
     */
    public int getRowSize() {
        return rowSize;
    }


    /**
     * Returns the Size of the Column in the table.
     * @return Integer of size of Column.
     */
    public int getColumnSize() {
        return columnSize;
    }


    /**
     * @param x    The x co-ordinate of the tile.
     * @param y    The y co-ordinate of the tile.
     * @param tile THe tile at the position.
     */
    public void insertTile(int x, int y, FloorTile tile) {
        tileCoordinates[x][y] = tile;
    }

    /**
     * @param x position of the desired tile
     * @param y position of the desired tile
     * @return tile in that location
     */
    public FloorTile getTileFromBoard(int x, int y) {
        return tileCoordinates[x][y];
    }


    /**
     * The method which is used to insert a new tile into a row.
     * @param c Cardinal direction
     * @param x the x position of the new Tile
     * @param y the y position of the new Tile
     * @param tile the new Tile
     * @return the old tile
     */
    public void insertTileIntoRow(Cardinals c, int x, int y, FloorTile tile) {
        FloorTile discardedTile;
        if (c == Cardinals.LEFT) { //push from left -> right
            discardedTile = getTileFromBoard(getRowSize() - 1, y);
            for (int row = getRowSize() - 1; row > 0; row--) {
                insertTile(row, y, getTileFromBoard(row - 1, y));
            }
            insertTile(0, y, tile);

        } else  { //push from right -> left
            discardedTile = getTileFromBoard(0, y);
            for (int row = 0; row < getRowSize() - 1; row++) {
                insertTile(row, y, getTileFromBoard(row + 1, y));
            }
            insertTile(getColumnSize() - 1, y, tile);
        }
    }


    /**
     * The method which is used to insert a new tile into a column.
     * @param c Cardinal direction
     * @param x the x position of the new Tile
     * @param y the y position of the new Tile
     * @param tile the new Tile
     * @return the old tile
     */
    public void insertTileIntoCol(Cardinals c, int x, int y, FloorTile tile) {
        FloorTile discardedTile;
        if (c == Cardinals.TOP) { //push from top -> bottom
            discardedTile = getTileFromBoard(x, getColumnSize() - 1);
            for (int row = getRowSize() - 1; row > 0; row--) {
                insertTile(row, y, getTileFromBoard(row - 1, y));
            }
            insertTile(x, 0, tile);

        } else  { //push from right -> left
            discardedTile = getTileFromBoard(x, 0);
            for (int row = 0; row < getRowSize() - 1; row++) {
                insertTile(row, y, getTileFromBoard(row + 1, y));
            }
            insertTile(x, getColumnSize() - 1, tile);
        }
    }

    /**
     * Check to see if any Fixed Tiles are present inside of a particular row of the board.
     *
     * @param y the row in question.
     * @return Boolean result.
     */
    public boolean checkTileInsertionRow(int y) {
        for (int x = 0; x < getRowSize() - 1; x++) {
            if (getTileFromBoard(x, y).isFixed() || getTileFromBoard(x, y).getState().equals("FROZEN")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check to see if any fixed tiles are present of a particular column of the board.
     *
     * @param x the column in question.
     * @return Boolean result.
     */
    public boolean checkTileInsertionCol(int x) {
        for (int y = 0; y < getColumnSize() - 1; y++) {
            if (getTileFromBoard(x, y).isFixed() || getTileFromBoard(x, y).getState().equals("FROZEN")) {
                return false;
            }
        }
        return true;
    }


    /**
     * Shift tiles depending on their cardinal direction placement.
     *
     * @param c    The cardinal direction placement.
     * @param x    The x co-ordinate where the player wants to slide tile in.
     * @param y    The y co-ordinate where the player wants to slide tile in.
     * @param tile The tile that is being slided in.
     */

    public FloorTile placeOnNewTile(Cardinals c, int x, int y, FloorTile tile) { //use enum for access cardinals on tiles
        if (c == Cardinals.TOP) {//shift index down from the second last (animations)
            FloorTile discardedTile = getTileFromBoard(x, 0);
            for (int row = getRowSize() - 1; row > 0; row--) {
//                if (getTileFromBoard(x, row - 1).getState().equals("FROZEN") ||
//                        getTileFromBoard(x, row - 1).getState().equals("FIRE")) {
//                    updateStatusKey(x, row, getTileFromBoard(x, row - 1));
//                } else {
                insertTile(x, row, getTileFromBoard(x, row - 1));
//                }
            }
            insertTile(x, 0, tile);
            return discardedTile;
        } else if (c == Cardinals.BOTTOM) {//push from bottom to up
            FloorTile discardedTile = getTileFromBoard(x, getRowSize() - 1);
            for (int row = 0; row < getRowSize() - 1; row++) {
//                if (getTileFromBoard(x, row + 1).getState().equals("FROZEN") ||
//                        getTileFromBoard(x, row + 1).getState().equals("FIRE")) {
//                    updateStatusKey(x, row, getTileFromBoard(x, row + 1));
//                } else {
                insertTile(x, row, getTileFromBoard(x, row + 1));
//                }
            }
            insertTile(x, getRowSize() - 1, tile);
            return discardedTile;
        } else if (c == Cardinals.LEFT) { //push from left -> right
            FloorTile discardedTile = getTileFromBoard(x, getColumnSize() - 1);
            for (int col = getColumnSize() - 1; col > 0; col--) {
//                if (getTileFromBoard(col - 1 , y).getState().equals("FROZEN") ||
//                        getTileFromBoard(col - 1, y).getState().equals("FIRE")) {
//                    updateStatusKey(col, y, getTileFromBoard(col - 1, y));
//                } else {
                insertTile(col, y, getTileFromBoard(col - 1, y));
//                }
            }
            insertTile(0, y, tile);
            return discardedTile;
        } else if (c == Cardinals.RIGHT) { //push from right -> left
            FloorTile discardedTile = getTileFromBoard(getColumnSize() - 1, y);
            for (int col = 0; col < getColumnSize() - 1; col++) {
//                if (getTileFromBoard(col + 1, y).getState().equals("FROZEN") ||
//                        getTileFromBoard(col + 1, y).getState().equals("FIRE")) {
//                    updateStatusKey(col, y, getTileFromBoard(col + 1, y));
//                } else {
                insertTile(col, y, getTileFromBoard(col + 1, y));
//                }
            }
            insertTile(getColumnSize() - 1, y, tile);
            return discardedTile;
        }
        return null;
    }




    /**
     * Search and store the goal co-ordinate on the board.
     *
     * @return
     */

    public int[] getGoal() {
        int[] cords = new int[2];
        for (int x = 0; x < getRowSize(); x++) {
            for (int y = 0; y < getColumnSize(); y++) {
                if (getTileFromBoard(x, y) instanceof GoalTile) {
                    cords[0] = x;
                    cords[1] = y;
                    return cords;
                }
            }
        }
        return null;
    }


    /**
     * @param x      The x co-ordinate of the player.
     * @param y      The y co-ordinate of the player.
     * @param player The player at the position.
     */

    public void insertPlayer(int x, int y, Player player) {
        playerCoordinates[x][y] = player;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public Player getPlayerFromBoard(int x, int y) {
        return playerCoordinates[x][y];
    }



}