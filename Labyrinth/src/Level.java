
public class Level {

    final Board boardData;
    final int gameTurnData;
    final String[] silkBagData;
    Player[] playerData;
    final int[] spawnPoints;


    /**
     * Template for a Saved Level
     * @param tempBoard contains the Board Object
     * @param gameTurn contains information regarding what turn it is
     * @param tempSilkBag contains information regarding the Tiles which are in the Silk Bag
     * @param players contains information of the players, and the profiles associated with them.
     */

    public Level(Board tempBoard, int gameTurn, String[] tempSilkBag, Player[] players) {
        this.boardData = tempBoard;
        this.gameTurnData = gameTurn;
        this.silkBagData = tempSilkBag;
        this.playerData = players;
        this.spawnPoints = null;
    }

    /**
     *  Template for a New Level
     * @param tempBoard contains the Board Object
     * @param gameTurn contains information regarding what turn it is
     * @param tempSilkBag contains information regarding the Tiles which are in the Silk Bag
     * @param spawnPoints contains information of the player spawn points.
     */

    public Level(Board tempBoard, int gameTurn, String[] tempSilkBag, int[] spawnPoints) {
        this.boardData = tempBoard;
        this.gameTurnData = gameTurn;
        this.silkBagData = tempSilkBag;
        this.spawnPoints = spawnPoints;
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

    public String[] getSilkBagData() {
        return silkBagData;
    }

    public int[] getSpawnPoints() {
        return spawnPoints;
    }

    public void setPlayerArray (Player[] Players) {
        this.playerData = Players;
    }

}
