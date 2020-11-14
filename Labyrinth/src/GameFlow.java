/**
 * @author Ben Dodd
 * @version 1.0.0
 */


public class GameFlow {
    private Board[] gameBoards;
    // private Player[] players;
    private int[] turns;

    // public GameFlow(Board board, Player[] players) {
    public GameFlow(Board[] gameBoards) {
        this.gameBoards = gameBoards;
        // this.players = players;
    }

    // public void playerSlotFloorTile(Board gameBoard, Tile tile, int x, int y) {
    public void playerSlotFloorTile() {}

    // public Boolean playerPlaceActionTile(Board gameBoard, Tile tile, int x, int y) {
    public Boolean playerPlaceActionTile() {
        return false;
    }

    // public Boolean checkActionCardValid(Tile tile, Player player) {
    public Boolean checkActionCardValid() {
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

    // public Player declareWinner() {
    public void declareWinner() {

    }

    public Boolean checkWin() {
        return false;
    }
}
