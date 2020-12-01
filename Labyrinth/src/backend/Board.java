package backend;

import Tiles.FloorTile;
import Tiles.GoalTile;

/**
 * The board class structures the game board where the methods are operations that directly affects what is going on
 * the game board. This includes tiles and players on the game board.
 *
 * @author Pat
 * @version 1.0
 */

public class Board {


    private final int rowSize;
    private final int columnSize;
    private String[] profileNames;
    private String nameOfBoard;
    private FloorTile[][] tileCoordinates;
    private Player[][] playerCoordinates;

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

    public String[] getProfileNames() {
        return profileNames;
    }

    public void setProfileNames(String[] profileNames) {
        this.profileNames = profileNames;
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
     * @param x    The x co-ordinate of the tile.
     * @param y    The y co-ordinate of the tile.
     * @param tile THe tile at the position.
     */
    public void insertTile(int x, int y, FloorTile tile) {
        tileCoordinates[x][y] = tile;
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
    public FloorTile getTileFromBoard(int x, int y) {
        return tileCoordinates[x][y];
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
     * @return
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     * @return
     */
    public int getColumnSize() {
        return columnSize;
    }

    /**
     * Check to see if any Fixed Tiles are present inside of a particular row of the board.
     *
     * @param y the row in question.
     * @return Boolean result.
     */
    public boolean checkTileInsertionRow(int y) {
        for (int x = 0; x < getRowSize(); x++) {
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
        for (int y = 0; y < getColumnSize(); y++) {
            if (getTileFromBoard(x, y).isFixed() || getTileFromBoard(x, y).getState().equals("FROZEN")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Set tiles to frozen state in a 3x3 area.
     *
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
     * Set tiles on file in a 3x3 area.
     *
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
                insertTile(x, row, getTileFromBoard(x, row - 1));
            }
            insertTile(x, 0, tile);
            return discardedTile;
        }

        if (c == Cardinals.BOTTOM) {//push from bottom to up
            FloorTile discardedTile = getTileFromBoard(x, getRowSize() - 1);
            for (int row = 0; row < getRowSize() - 1; row++) {
                insertTile(x, row, getTileFromBoard(x, row + 1));
            }
            insertTile(x, getRowSize() - 1, tile);
            return discardedTile;
        }

        if (c == Cardinals.LEFT) { //push from left -> right
            FloorTile discardedTile = getTileFromBoard(x, getColumnSize() - 1);
            for (int col = getColumnSize() - 1; col > 0; col--) {
                insertTile(col, y, getTileFromBoard(col - 1, y));
            }
            insertTile(0, y, tile);
            return discardedTile;
        }

        if (c == Cardinals.RIGHT) { //push from right -> left
            FloorTile discardedTile = getTileFromBoard(getColumnSize() - 1, y);
            for (int col = 0; col < getColumnSize() - 1; col++) {
                insertTile(col, y, getTileFromBoard(col + 1, y));
            }
            insertTile(getColumnSize() - 1, y, tile);
            return discardedTile;
        }
        return null;
    }

    /**
     * Move player when their player piece is at the end of the tile placement.
     *
     * @param x The x co-ordinate of the new position of the player.
     * @param y The y co-ordinate of the new position of the player.
     * @param c The cardinal place of the tile insertion.
     */
    public void movePlayerFromEndTile(int x, int y, Cardinals c) {
        if (checkIfPlayerEndTile(x, y, c)) {
            if (c == Cardinals.TOP) {
                movePlayer(x, y, x, getColumnSize());
            } else if (c == Cardinals.BOTTOM) {
                movePlayer(x, y, x, 0);
            } else if (c == Cardinals.LEFT) {
                movePlayer(x, y, getRowSize(), y);
            } else if (c == Cardinals.RIGHT) {
                movePlayer(x, y, 0, y);
            }
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
            return getPlayerFromBoard(x, getColumnSize()) != null;
        } else if (c == Cardinals.BOTTOM) {
            return getPlayerFromBoard(x, 0) != null;
        } else if (c == Cardinals.LEFT) {
            return getPlayerFromBoard(getRowSize(), y) != null;
        } else if (c == Cardinals.RIGHT) {
            return getPlayerFromBoard(0, y) != null;
        }
        return false;
    }

    /**
     * Checks to see if their is a player at the end of the tile.
     *
     * @param x The x co-ordinate of the tile on the end.
     * @param y The y co-ordinate of the tile on the end.
     * @return The Boolean value true or false.
     */
    public Boolean checkPlayerEndTile(int x, int y) {
        return getPlayerFromBoard(x, y) != null;
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

    /**
     * Moves player to a new position.
     *
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
     * Set the player back to their previous co-ordinate.
     *
     * @param tilesVisited The player's previous co-ordinates history.
     * @param x            The new x co-ordinate of the player.
     * @param y            The new y co-ordinate of the player.
     */
    public void backTrackPlayer(int[] tilesVisited, int x, int y) {
        if (getTileFromBoard(tilesVisited[2], tilesVisited[3]).getState().equals("FIRE")) {
            throw new IllegalArgumentException("Tile is on fire, select another tile!"); //TODO need to bring to frontend
        } else {
            movePlayer(tilesVisited[2], tilesVisited[3], x, y);
        }
    }

    /**
     * Search and store the goal co-ordinate on the board.
     *
     * @return
     */
    public int[] getGoal() {
        int[] coords = new int[2];
        for (int x = 0; x < getRowSize(); x++) {
            for (int y = 0; y < getColumnSize(); y++) {
                if (getTileFromBoard(x, y) instanceof GoalTile) {
                    coords[0] = x;
                    coords[1] = y;
                    return coords;
                }
            }
        }
        return null;
    }

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


}