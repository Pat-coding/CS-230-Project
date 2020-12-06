package backend;
import java.util.ArrayList;

/**
 * This class can get the information from a saved game or a new game and input into board to start a game.
 * @author Ashley Hayre
 * @version 1.0
 */

public class Level {

    public static ArrayList<Profile> profileArray =
            FileManager.readProfileDataFile("Profiles.txt");
    public static ArrayList<Level> newLevels =
            FileManager.readLevelDataFile("NewLevel.txt", "New Level");
    public static ArrayList<Level> savedLevels =
            FileManager.readLevelDataFile("SavedLevel.txt", "Saved Level");

    private static int playerIndex;
    public boolean saveButtonFlag;
    public boolean pressLeftFlag;
    public boolean pressRightFlag;
    public boolean pressDownFlag;
    public boolean pressUpFlag;
    public boolean drawTileFlag;
    public boolean playerHasMovedFlag;
    public boolean endTurnFlag;
    public boolean playerHandFlag;
    private int tempX;
    private int tempY;
    private Board.Cardinals tempCardinal;
    private boolean playerHasSlotTile;
    private Board boardData;
    private int gameTurnData;
    private SilkBag silkBag;
    private Player[] playerData;
    private int[] spawnPoints;

    /**
     * Template for a Saved Level.
     *
     * @param tempBoard contains the Board Object
     * @param gameTurn  contains information regarding what turn it is
     * @param SilkBag   contains information regarding the Tiles which are in the Silk Bag
     * @param players   contains information of the players, and the profiles associated with them.
     */

    public Level(Board tempBoard, int gameTurn, SilkBag SilkBag, Player[] players) {
        this.boardData = tempBoard;
        this.gameTurnData = gameTurn;
        this.silkBag = SilkBag;
        this.playerData = players;
        this.spawnPoints = null;
    }


    /**
     * Template for a New Level.
     *
     * @param tempBoard   contains the Board Object
     * @param gameTurn    contains information regarding what turn it is
     * @param SilkBag     contains information regarding the Tiles which are in the Silk Bag
     * @param spawnPoints contains information of the player spawn points.
     */

    public Level(Board tempBoard, int gameTurn, SilkBag SilkBag, int[] spawnPoints) {
        this.boardData = tempBoard;
        this.gameTurnData = gameTurn;
        this.spawnPoints = spawnPoints;
        this.silkBag = SilkBag;
        this.playerData = null;
    }

    /**
     *This method gets the index of the curreent player.
     * @return player index
     */
    public static int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * This method sets the player index.
     * @param playerIndex Players index
     */
    public static void setPlayerIndex(int playerIndex) {
        Level.playerIndex = playerIndex;
    }

    /**
     * Gets profile array.
     * @return array of profiles
     */
    public static ArrayList<Profile> getProfileArray() {
        return profileArray;
    }

    /**
     * Gets all the preset levels.
     * @return array
     */
    public static ArrayList<Level> getNewLevels() {
        return newLevels;
    }

    /**
     * Gets all saved levels.
     * @return array of saved levels
     */
    public static ArrayList<Level> getSavedLevels() {
        return savedLevels;
    }

    /**
     * Gets current board data.
     * @return board data
     */
    public Board getBoardData() {
        return boardData;
    }

    /**
     * Sets the current board from saved level.
     * @param board board information
     *
     */
    public void setBoardData(Board board) {
        this.boardData = board;
    }

    /**
     * Gets index of current player.
     * @return player index
     */
    public int getGameTurnData() {
        return gameTurnData;
    }

    /**
     * Gets all players from current level.
     * @return array of players
     */
    public Player[] getPlayerData() {
        return playerData;
    }

    /**
     * Gets current silk bag.
     * @return silk bag
     */
    public SilkBag getSilkBag() {
        return silkBag;
    }

    /**
     * This methods sets silk bag to silk bag from the level.
     * @param silkBag content of silkbag
     */
    public void setSilkBag(SilkBag silkBag) {
        this.silkBag = silkBag;
    }

    /**
     * Returns all possible spawn points in the level.
     * @return array of ints
     */
    public int[] getSpawnPoints() {
        return spawnPoints;
    }

    /**
     * Creates an array of players in the level.
     * @param Players creates player array
     */
    public void setPlayerArray(Player[] Players) {
        this.playerData = Players;
    }

    /**
     * Sets the current game turn.
     * @param gameTurn game turn
     */
    public void setGameTurn(int gameTurn) {
        this.gameTurnData = gameTurn;
    }

    /**
     * Gets temporary X.
     * @return in of x
     */
    public int getTempX() {
        return tempX;
    }

    /**
     * Sets temporary x.
     * @param tempX the temp value of x
     */
    public void setTempX(int tempX) {
        this.tempX = tempX;
    }

    /**
     * Sets temporary y.
     * @return tempy
     */
    public int getTempY() {
        return tempY;
    }

    /**
     * Sets temporary y.
     * @param tempY the temp value of y
     */
    public void setTempY(int tempY) {
        this.tempY = tempY;
    }

    /**
     * Gets temporary cardinal.
     * @return cardinal direction
     */
    public Board.Cardinals getTempCardinal() {
        return tempCardinal;
    }

    /**
     * Sets temporary cardinal.
     * @param tempCardinal cardinal direction
     */
    public void setTempCardinal(Board.Cardinals tempCardinal) {
        this.tempCardinal = tempCardinal;
    }

    public boolean isPlayerHasSlotTile() {
        return playerHasSlotTile;
    }

    public void setPlayerHasSlotTile(boolean playerHasSlotTile) {
        this.playerHasSlotTile = playerHasSlotTile;
    }
}
