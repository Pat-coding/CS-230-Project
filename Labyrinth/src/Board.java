/**
 * @author Pat, Ash
 * @version 1.0
 */


import Tile;
import Player;


public class Board {
    // 90, 180, 270

//    0 1 2 3 4 5
//  0 A F
//  1
//  2
//  3
//  4
//  5

    private String nameOfBoard;
    private final int rowSize;
    private final int columnSize;
    private Tile[][] tileCoordinates;
    private Player[][] playerCoordinates;

    //  Tile (Type, Orientation, State, fixed)
    public Board (String nameOfBoardInput, int[] sizeOfBoard) {
        rowSize = sizeOfBoard[0];
        columnSize = sizeOfBoard[1];

        nameOfBoard = nameOfBoardInput;
        tileCoordinates = new Tile[columnSize][rowSize];
        playerCoordinates = new Player[columnSize][rowSize];

    }

    public void insertTile (int x, int y, Tile tile) {
        tileCoordinates[x][y] = tile;
    }

    public void insertPlayer (int x, int y, Player player) {
        playerCoordinates[x][y] = Player;
    }

    public Tile getTileFromBoard (int x, int y) {
        return tileCoordinates[x][y];
    }

    public Player getPlayerFromBoard (int x, int y) {
        return playerCoordinates[x][y];
    }

    public int getRowSize() {
        return rowSize;
    }

    /**
     * This Method check to see if any Fixed Tiles are present inside of a particular row, of a Board.
     *
     * @param y the row in question
     * @return Boolean result
     */

    public boolean checkTileInsersionRow(int y) {
        for (int x = 0; x < getRowSize(); x++) {
            if ((getTileFromBoard(x, y).isFixed || getTileFromBoard (x, y).isFrozen) == True) {
                return False;
            }
        }
        return True;
    }

    //private boolean checkTilePlacementCol(int y) {
//        for (int i = 0; i < boardY; i++) {
            // if (tile.fixed == true || tile.frozen == true){return false} something like this, no clue XD
    //    }

}
