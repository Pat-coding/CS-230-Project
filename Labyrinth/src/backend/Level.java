package backend;

public class Level {

    final Board boardData;
    final int gameTurnData;
    SilkBag silkBagObject;
    Player[] playerData;
    final int[] spawnPoints;


    /**
     * Template for a Saved Level
     * @param tempBoard contains the Board Object
     * @param gameTurn contains information regarding what turn it is
     * @param SilkBag contains information regarding the Tiles which are in the Silk Bag
     * @param players contains information of the players, and the profiles associated with them.
     */

    public Level(Board tempBoard, int gameTurn, SilkBag SilkBag, Player[] players) {
        this.boardData = tempBoard;
        this.gameTurnData = gameTurn;
        this.silkBagObject = SilkBag;
        this.playerData = players;
        this.spawnPoints = null;
    }

    /**
     *  Template for a New Level
     * @param tempBoard contains the Board Object
     * @param gameTurn contains information regarding what turn it is
     * @param SilkBag contains information regarding the Tiles which are in the Silk Bag
     * @param spawnPoints contains information of the player spawn points.
     */

    public Level(Board tempBoard, int gameTurn, SilkBag SilkBag, int[] spawnPoints) {
        this.boardData = tempBoard;
        this.gameTurnData = gameTurn;
        this.spawnPoints = spawnPoints;
        this.silkBagObject = SilkBag;
        this.playerData = null;
    }

    public Board getBoardData() {
        return boardData;
    }

    public int getGameTurnData() {
        return gameTurnData;
    }

    public Player[] getPlayerData() {
        return playerData;
    }

    public SilkBag getSilkBagObject() {
        return silkBagObject;
    }

    public int[] getSpawnPoints() {
        return spawnPoints;
    }

    public void setPlayerArray (Player[] Players) {
        this.playerData = Players;
    }

}
