
public class Level {

    final Board boardData;
    final int gameTurnData;
    final String[] silkBagData;
    final Player[] playerData;
    final int[] spawnPoints;

    public Level(Board tempBoard, int gameTurn, String[] tempSilkBag, Player[] players) {
        this.boardData = tempBoard;
        this.gameTurnData = gameTurn;
        this.silkBagData = tempSilkBag;
        this.playerData = players;
        this.spawnPoints = null;
    }

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

    public int[] getSpawnPoints() {return spawnPoints;}

}
