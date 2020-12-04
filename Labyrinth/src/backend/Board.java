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
    public Player tempPlayer;

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
     * Check to see if any Fixed Tiles are present inside of a particular row of the board.
     *
     * @param y the row in question.
     * @return Boolean result.
     */
    public boolean checkTileInsertionRow(int y) {
        for (int x = 0; x < getRowSize() - 1; x++) {
            if (getTileFromBoard(x, y).isFixed()) {
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
            if (getTileFromBoard(x, y).isFixed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves player to a new position.
     *
     * @param newX The x co-ordinate of the new position.
     * @param newY The y co-ordinate of the new position.
     * @param oldX The x co-ordinate of the old position.
     * @param oldY The x co-ordinate of the old position.
     */
    public void movePlayer(int oldX, int oldY, int newX, int newY) {
            insertPlayer(newX, newY, getPlayerFromBoard(oldX, oldY));
            insertPlayer(oldX, oldY, null);

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
                slidePlayerWithBoard(c, x, row);
                insertTile(x, row, getTileFromBoard(x, row - 1));
//                }
            }
            insertTile(x, 0, tile);
            if(tempPlayer != null) {
                insertPlayer(x, 0, tempPlayer);
                tempPlayer = null;
            }
            return discardedTile;
        } else if (c == Cardinals.BOTTOM) {//push from bottom to up
            FloorTile discardedTile = getTileFromBoard(x, getRowSize() - 1);
            for (int row = 0; row < getRowSize() - 1; row++) {
//                if (getTileFromBoard(x, row + 1).getState().equals("FROZEN") ||
//                        getTileFromBoard(x, row + 1).getState().equals("FIRE")) {
//                    updateStatusKey(x, row, getTileFromBoard(x, row + 1));
//                } else {
                slidePlayerWithBoard(c, x, row);
                insertTile(x, row, getTileFromBoard(x, row + 1));

//                }
            }
            insertTile(x, getRowSize() - 1, tile);
            if(tempPlayer != null) {
                insertPlayer(x, getRowSize() - 1, tempPlayer);
                tempPlayer = null;
            }
            return discardedTile;
        } else if (c == Cardinals.LEFT) { //push from left -> right
            FloorTile discardedTile = getTileFromBoard(x, getColumnSize() - 1);
            for (int col = getColumnSize() - 1; col > 0; col--) {
//                if (getTileFromBoard(col - 1 , y).getState().equals("FROZEN") ||
//                        getTileFromBoard(col - 1, y).getState().equals("FIRE")) {
//                    updateStatusKey(col, y, getTileFromBoard(col - 1, y));
//                } else {
                slidePlayerWithBoard(c, col, y);
                insertTile(col, y, getTileFromBoard(col - 1, y));

//                }
            }
            insertTile(0, y, tile);
            if(tempPlayer != null) {
                insertPlayer(0, y, tempPlayer);
                tempPlayer = null;
            }
            return discardedTile;
        } else if (c == Cardinals.RIGHT) { //push from right -> left
            FloorTile discardedTile = getTileFromBoard(getColumnSize() - 1, y);
            for (int col = 0; col < getColumnSize() - 1; col++) {
//                if (getTileFromBoard(col + 1, y).getState().equals("FROZEN") ||
//                        getTileFromBoard(col + 1, y).getState().equals("FIRE")) {
//                    updateStatusKey(col, y, getTileFromBoard(col + 1, y));
//                } else {
                slidePlayerWithBoard(c, col, y);
                insertTile(col, y, getTileFromBoard(col + 1, y));

//                }
            }
            insertTile(getColumnSize() - 1, y, tile);
            if(tempPlayer != null) {
                insertPlayer(getColumnSize() - 1, y, tempPlayer);
                tempPlayer = null;
            }
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

    public void slidePlayerWithBoard(Cardinals c, int x, int y) {
        //if there is no player on the next tile or a player at the end of the tile
        if(getPlayerFromBoard(x, y) == null || !checkIfPlayerEndTile(x, y, c)) {
            if (c == Cardinals.TOP || getPlayerFromBoard(x, y) != null ) {
                movePlayer(x, y - 1, x, y);
            } else if (c == Cardinals.BOTTOM || getPlayerFromBoard(x, y) != null) {
                movePlayer(x, y + 1, x, y);
            } else if (c == Cardinals.LEFT || getPlayerFromBoard(x, y) != null) {
                movePlayer(x - 1, y, x, y);
            } else if (c == Cardinals.RIGHT || getPlayerFromBoard(x, y) != null) {
                movePlayer(x + 1, y, x, y);
            }
            //if there is a player at the end of the tile or there is no player on the next tile
        } else if(getPlayerFromBoard(x, y) == null || checkIfPlayerEndTile(x, y, c)) {
            movePlayerFromEndTile(c, x, y);
            //
        } else if(getPlayerFromBoard(x, y) != null || !checkIfPlayerEndTile(x, y, c)) {
            System.out.println("hello123");
        }
    }

    /**
     * Method checks if there is a player at the end of the tile.
     *
     * @param x
     * @param y
     * @param c
     * @return
     */
    private boolean checkIfPlayerEndTile(int x, int y, Cardinals c) {
        if (c == Cardinals.TOP) {
            return getPlayerFromBoard(x, getColumnSize() - 1) != null;
        } else if (c == Cardinals.BOTTOM) {
            return getPlayerFromBoard(x, 0) != null;
        } else if (c == Cardinals.LEFT) {
            return getPlayerFromBoard(getRowSize() - 1, y) != null;
        } else if (c == Cardinals.RIGHT) {
            return getPlayerFromBoard(0, y) != null;
        }
        return false;

    }

    /**
     * Move player when their player piece is at the end of the tile placement.
     *
     * @param x The x co-ordinate of the new position of the player.
     * @param y The y co-ordinate of the new position of the player.
     * @param c The cardinal place of the tile insertion.
     */
    public void movePlayerFromEndTile(Cardinals c, int x, int y) {
        if (c == Cardinals.TOP && getPlayerFromBoard(x, 0) == null ) {
            tempPlayer = getPlayerFromBoard(x, y);
            movePlayer(x, y - 1, x, y);
        } else if (c == Cardinals.BOTTOM && getPlayerFromBoard(x, getColumnSize() - 1) == null) {
            tempPlayer = getPlayerFromBoard(x, y);
            movePlayer(x, y + 1, x, y);
        } else if (c == Cardinals.LEFT && getPlayerFromBoard(0, y) == null) {
            tempPlayer = getPlayerFromBoard(x , y);
            movePlayer(x - 1, y, x, y);
        } else if (c == Cardinals.RIGHT && getPlayerFromBoard(getRowSize() - 1, y) == null) {
            tempPlayer = getPlayerFromBoard(x, y);
            movePlayer(x + 1, y, x, y);
        }
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

    /**
     * Searches and store player information.
     *
     * @param player The player in search .
     * @return The x and y co-ordinate in as an int array.
     */
    public int[] playerLocationOnBoard(Player player) {
        int[] cords = new int[2];
        for (int x = 0; x < getRowSize(); x++) {
            for (int y = 0; y < getColumnSize(); y++) {
                if (getPlayerFromBoard(x, y) == player) {
                    cords[0] = x;
                    cords[1] = y;
                    return cords;
                }
            }
        }
        return null;
    }


}