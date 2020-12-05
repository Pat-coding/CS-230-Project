package backend;
import java.util.ArrayList;
public class Level {

    /**
     *
     * @author
     *
     */

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

    private Board boardData;
    private int gameTurnData;
    private SilkBag silkBag;
    private Player[] playerData;
    private int[] spawnPoints;

    /**
     * Template for a Saved Level
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
     * Template for a New Level
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
     *
     * @return
     */
    public static int getPlayerIndex() {
        return playerIndex;
    }

    /**
     *
     * @param playerIndex
     */
    public static void setPlayerIndex(int playerIndex) {
        Level.playerIndex = playerIndex;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Profile> getProfileArray() {
        return profileArray;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Level> getNewLevels() {
        return newLevels;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Level> getSavedLevels() {
        return savedLevels;
    }

    /**
     *
     * @return
     */
    public Board getBoardData() {
        return boardData;
    }

    /**
     *
     * @param board
     */
    public void setBoardData(Board board) {
        this.boardData = board;
    }

    /**
     *
     * @return
     */
    public int getGameTurnData() {
        return gameTurnData;
    }

    /**
     *
     * @return
     */
    public Player[] getPlayerData() {
        return playerData;
    }

    /**
     *
     * @return
     */
    public SilkBag getSilkBag() {
        return silkBag;
    }

    /**
     *
     * @param silkBag
     */
    public void setSilkBag(SilkBag silkBag) {
        this.silkBag = silkBag;
    }

    /**
     *
     * @return
     */
    public int[] getSpawnPoints() {
        return spawnPoints;
    }

    /**
     *
     * @param Players
     */
    public void setPlayerArray(Player[] Players) {
        this.playerData = Players;
    }

    /**
     *
     * @param gameTurn
     */
    public void setGameTurn(int gameTurn) {
        this.gameTurnData = gameTurn;
    }

    /**
     *
     * @return
     */
    public int getTempX() {
        return tempX;
    }

    /**
     *
     * @param tempX
     */
    public void setTempX(int tempX) {
        this.tempX = tempX;
    }

    /**
     *
     * @return
     */
    public int getTempY() {
        return tempY;
    }

    /**
     *
     * @param tempY
     */
    public void setTempY(int tempY) {
        this.tempY = tempY;
    }

    /**
     *
     * @return
     */
    public Board.Cardinals getTempCardinal() {
        return tempCardinal;
    }

    /**
     *
     * @param tempCardinal
     */
    public void setTempCardinal(Board.Cardinals tempCardinal) {
        this.tempCardinal = tempCardinal;
    }
}
