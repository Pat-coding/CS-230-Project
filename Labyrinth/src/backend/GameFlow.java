package backend;

/**
 * @author Ben Dodd
 * @version 1.0.0
 */

import Tiles.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameFlow {
    private Level level;
    private Player[] players;
    private int playerTurn;

    /**
     * New Game
     * @param level Level to play.
     * @param profiles Profiles to create players from.
     */
    public GameFlow(Level level, Profile[] profiles) {
        this.level = level;
        this.initiatePlayers(profiles);
        // Randomly select a player to have the first turn.
        Random r = new Random();
        this.playerTurn = r.nextInt(this.players.length);
        this.players[this.playerTurn].playerTurn();
    }

    /**
     * Continue Level
     * @param level Level to play.
     */

    public GameFlow(Level level) {
        level = level;
        players = level.getPlayerData();
        // Set the player turn to whichever player had the last turn in the previous save.
        for (int i = 0; i < this.players.length; i++) {
            if (players[i].getPlayerTurn() == true) {
                playerTurn = i;
                break;
            }
        }
    }

    /**
     * Create players based on level data and profiles.
     * @param profiles Profiles to initialise players from.
     */

    private void initiatePlayers(Profile[] profiles) {
        players = new Player[profiles.length];
        int[] spawnPoints = level.getSpawnPoints();
        for (int i = 0; i < profiles.length; i++) {
            players[i] = new Player(profiles[i], spawnPoints[i*2], spawnPoints[(i*2) + 1], new int[6],
                    new ArrayList<Tile>(), false, false);
        }
        level.setPlayerArray(this.players);
    }

    /** Populate board **/

    private void populateBoard(Board board) {

    }


    /**
     * Insert a tile onto the board at the specified coordinates.
     * The tile that is taken off the board will be returned to be inserted into the silk bag.
     * @param tile          The tile to add to the board.
     * @param x             The x-coordinate to insert the tile into.
     * @param y             The y-coordinate to insert the tile into.
     * @return              The tile that was removed from the board.
     */
    public FloorTile check(Board.Cardinals direction, FloorTile tile, int x, int y) {
        level.getBoardData().movePlayerFromEndTile(x, y, direction);
        return level.getBoardData().placeOnNewTile(direction, x, y, tile);
    }


    /**
     * Place an action tile at the given coordinates on the board.
     * @param tile The tile to place on the board.
     * @param x The x-coordinate to insert the action tile.
     * @param y The y-coordinate to insert the action tile.
     * @return True if the tile was placed.
     */
    public Boolean playerPlaceActionTile(ActionTile tile, int x, int y) {
        // If the player of the current turn is trying to place an action tile
        // on themselves, we deny it.
        if (!checkActionCardValid(this.players[playerTurn], x, y)) {
            return false;
        }
        // check type of tile
        if (tile instanceof FireTile) {
            this.level.getBoardData().setTilesOnFire(x, y);
            return true;
        } else if (tile instanceof IceTile) {
            this.level.getBoardData().setTilesFrozen(x, y);
            return true;
        }
        return false;
    }

    /**
     * Checks if the player is standing on a point where they want to place an action tile.
     * @param player The player requesting the placement.
     * @return True if the tile can be placed.
     */
    public Boolean checkActionCardValid(Player player, int x, int y) {
        return Arrays.equals(level.getBoardData().playerLocationOnBoard(x, y, player), new int[] {x, y});
    }

    public Boolean checkBackTrackValid(int x, int y) {
        //  INVALID
        if (level.getBoardData().getPlayerFromBoard(x, y).getBackTrackCheck() == true) {
            return true;
        }
        return false;
    }

    /**
     * Go to the next turn of the board.
     */


    /**  TODO Connects with constructor
     *
     *
     *
     *
     *      Managing Turns
     *
     *      STARTING PHASE
     *          OPTIONAL - SAVE GAME
     *
     *
     *      DRAWING PHASE - DRAW TILE
     *          WAIT TILL PLAYER HAS DRAWN FROM SILK BAG
     *          THEN PROCEED
     *
     *
     *      OPTIONAL PHASE - PLACING TILE
     *          Checks to see if action performed on player turn is legal
     *              if legal, proceed
     *                  CHECK IF THEY WIN BEFORE PROCEEDING
     *              else, cancel action.
     *
     *      MOVING PHASE
     *          Takes keystrokes, and moves in that direction - Proceeds to END TURN PHASE
     *              CHECKS FOR OBSTRUCTION
     *                  IF obstruction is detected FLAG TRUE
     *                      SHOWS END TURN BUTTON
     *
     *
     *      ENDING TURN PHASE
     *          Check to see if player has won.
     *              If true
     *                  Increment player Win count
     *                  Increment OTHER player loss count
     *                  END GAME
     *                      IF SAVED GAME
     *                          DELETE SAVE FILE
     *             If false
     *                  Next Player Turn
     *                      SETTING THIS player[x].isPlayerTurn to False
     *                      Set player[x + 1].isPlayerTurn to True
     *                          If player[x + 1] = 4
     *                              THEN player[0].isPlayerTurn to True
     *
     * **/


    public void flow(Player[] player) {
        // constructor which connects to deniz part here

        // while(Winner = false){

        // }


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