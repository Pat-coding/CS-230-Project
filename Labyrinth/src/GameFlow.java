/**
 * @author Ben Dodd
 * @version 1.0.0
 */


public class GameFlow {
    private Board[] gameBoards; // Stores each board created
    private Player[] players;
    private int[] turns; // Stores the current turn for each board created

    public GameFlow(Board[] gameBoards, Player[] players) {
        this.gameBoards = gameBoards;
        this.players = players;
    }

    public void playerSlotFloorTile(Board gameBoard, Tile tile, int x, int y) {}

    public Boolean playerPlaceActionTile(Board gameBoard, Tile tile, int x, int y) {
        return false;
    }

    public Boolean checkActionCardValid(Tile tile, Player player) {
        return false;
    }

    public void incGameTurn() {
    }

    public Boolean endGame() {
        return false;
    }

    public Boolean saveGame() {
        return false;
    }

    public Player declareWinner() {
        return null;
    }

    public Boolean checkWin() {
        return false;
    }
}
