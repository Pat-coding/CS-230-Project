/**
 * @author Ben Dodd
 * @version 1.0.0
 */


public class GameFlow {
    private Board gameBoards; // Stores each board created
    private Player[] players;
    private int turn; // Stores the current turn for each board created

    /**
     * Create GameFlow logic for a specific board.
     * @param gameBoard The board to put game logic on.
     * @param players The players of the board.
     * @param turn The current turn on the board.
     */
    public GameFlow(Board gameBoard, Player[] players, int turn) {
        this.gameBoards = gameBoard;
        this.players = players;
        this.turn = turn;
    }

    /**
     * Insert a tile onto the board at the specified coordinates.
     * The tile that is taken off the board will be returned to be inserted into the silk bag.
     * @param gameBoard     The board that the tile is being added to.
     * @param tile          The tile to add to the board.
     * @param x             The x-coordinate to insert the tile into.
     * @param y             The y-coordinate to insert the tile into.
     * @return              The tile that was removed from the board.
     */
    public Tile playerSlotFloorTile(Board gameBoard, Tile tile, int x, int y) {
        return null;
    }

    /**
     * Place an action tile at the given coordinates on the board.
     * @param gameBoard The board to place the tile on.
     * @param tile The tile to place on the board.
     * @param x The x-coordinate to insert the action tile.
     * @param y The y-coordinate to insert the action tile.
     * @return True if the tile was placed.
     */
    public Boolean playerPlaceActionTile(Board gameBoard, Tile tile, int x, int y) {
        return false;
    }

    /**
     * Checks if the player is standing on a point where they want to place an action tile.
     * @param tile The tile the player wants to place.
     * @param player The player requesting the placement.
     * @return True if the tile can be placed.
     */
    public Boolean checkActionCardValid(Tile tile, Player player) {
        return false;
    }

    /**
     * Go to the next turn of the board.
     */
    public void incGameTurn() {
    }

    /**
     * Prepare the game to finish, either for saving or at a win.
     * @return True if the game could end
     */
    public Boolean endGame() {
        return false;
    }

    /**
     * Pass data from the game to be saved.
     * @return True if the game could be saved.
     */
    public Boolean saveGame() {
        return false;
    }

    /**
     * Announces that a player has won.
     * @return Player that won.
     */
    public Player declareWinner() {
        return null;
    }

    /**
     * Check if the board is in a state where a player has won.
     * @return True if there is a winning situation. 
     */
    public Boolean checkWin() {
        return false;
    }
}
