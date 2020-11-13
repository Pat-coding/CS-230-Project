
import Board;
import Player;

public class Level {

    final Board boardData;
    final int gameTurnData;
    final String[] silkBagData;
    final Player[] playerData;

    public Level(Board tempBoard, int gameTurn, String[] tempSilkBag, Player[] players) {
        boardData = tempBoard;
        gameTurnData = gameTurn;
        silkBagData = tempSilkBag;
        playerData = players;
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

}
