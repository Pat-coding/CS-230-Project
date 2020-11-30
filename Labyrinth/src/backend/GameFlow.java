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
    private Boolean drawButton;

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
     *
     * @param direction
     * @param tile
     * @param x
     * @param y
     * @return
     */
    public FloorTile slotTiles(Board.Cardinals direction, FloorTile tile, int x, int y) {
        level.getBoardData().movePlayerFromEndTile(x, y, direction);
        return level.getBoardData().placeOnNewTile(direction, x, y, tile);
    }

    /**
     *
     * @param x
     * @param y
     * @param player
     */
    public void movePlayer(int x, int y, int player) {
        level.boardData.movePlayer(level.playerData[player].getPlayerCordX(),level.playerData[player].getPlayerCordY(),
               x, y );
        checkWin();
    }

    /**
     *
     * @param x
     * @param y
     */
    public void playerPlaceIce(int x, int y) {
        level.getBoardData().setTilesFrozen(x, y);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void playerPlaceFire(int x, int y) {
        level.getBoardData().setTilesOnFire(x, y);
    }

    /**
     *
     * @param player
     * @param x
     * @param y
     */
    public void playerPlaceDouble(int player, int x, int y) {
        movePlayer(x, y, player);
        movePlayer(x, y, player); //TODO need to change
    }

    /**
     *
     * @param player
     */
    public void playerPlaceBack(int player) {
        level.boardData.backTrackPlayer(level.getPlayerData()[player].getProfileCordHistory(),
                level.getPlayerData()[player].getPlayerCordX(), level.getPlayerData()[player].getPlayerCordY());
    }

    /**
     *
     * @param tile
     * @param player
     * @param x
     * @param y
     * @return
     */
    public Boolean checkWhichActionTile(ActionTile tile, int player, int x, int y) {
        if (tile instanceof FireTile) {
            checkActionCardValid(x, y);
            playerPlaceFire(x, y);
            return true;
        } else if (tile instanceof IceTile) {
            checkActionCardValid(x, y);
            playerPlaceIce(x, y);
            return true;
        } else if (tile instanceof DoubleMoveTile) {
            playerPlaceDouble(player, x, y);
            return true;
        } else {
            checkBackTrackValid(player);
            playerPlaceBack(player);
            return true;
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Boolean checkActionCardValid(int x, int y) {

        for(int i = 0; i < level.playerData.length; i++) {
             if (Arrays.equals(level.getBoardData().playerLocationOnBoard(x, y, level.playerData[i]),
                    new int[] {x, y})) {
                 return false;
             }
        }
        return true;
    }

    /**
     *
     * @param player
     * @return
     */
    public Boolean checkBackTrackValid(int player) {
        return !level.playerData[player].getBackTrackCheck();

    }

    /**
     *
     */
    public void playerDraw(int i) {
        level.silkBag.giveTile(level.playerData[i]);
        setDrawButton(true);
    }

    /**  TODO Connects with constructor
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
     *              CHECK FOR ACTION TILES, AS WELL AS TILE PLACEMENTS
     *              if legal, proceed
     *                  CHECK IF THEY WIN BEFORE PROCEEDING
     *              else, cancel action.
     *
     *      MOVING PHASE (ACCOUNT FOR DOUBLE MOVE PLEASE!!!)
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
         while(!checkWin()){

             //if(players clicks save game button then ) {saveGame()}

             if (getDrawButton()) {
                 // pass to fronteend to display message saying please do an action
             } else {
                 // pass to frontend to display message saying please draw a card
             }

             //if(Player use action card) {playerPlaceFireIceTile }

             }
         }
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
    public void declareWinner() {

    }

    /**
     * Check if the board is in a state where a player has won.
     * @return True if there is a winning situation.
     */
    public Boolean checkWin() {
        if(level.boardData.getPlayerFromBoard(level.boardData.getGoal()[0], level.boardData.getGoal()[1]) != null) {
            declareWinner();
            return true;
        }
        return false;
    }

    public Boolean getDrawButton() {
        return drawButton;
    }

    public void setDrawButton(Boolean drawButton) {
        this.drawButton = drawButton;
    }
}