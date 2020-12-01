package backend;

/**
 * @author Ben Dodd
 * @version 1.0.0
 */

import Tiles.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GameFlow {
    private Level level;
    private Player[] players;
    private int playerTurn;
    private Boolean drawButton;

    /**
     * New Game
     *
     * @param level    Level to play.
     * @param profiles Profiles to create players from.
     */
    public GameFlow(Level level, Profile[] profiles) {
        this.level = level;
        this.initiatePlayers(profiles);

        this.players = this.level.getPlayerData();
    }

    /**
     * Continue Level
     *
     * @param level Level to play.
     */

    public GameFlow(Level level) {
        this.level = level;
        this.players = level.getPlayerData();

        this.players = this.level.getPlayerData();
    }

    public static void saveGame() {
        //  Override previous save game
        if (!saveGameCheck()) {
            level.getSavedLevels().add(this.level);
        }
    }

    /**
     * Check if the board is in a state where a player has won.
     *
     * @return True if there is a winning situation.
     */
    public static Boolean checkWin() {
        if (level.getBoardData().getPlayerFromBoard(level.getBoardData().getGoal()[0],
                level.getBoardData().getGoal()[1]) != null) {
            declareWinner();
            return true;
        }
        return false;
    }

    /**
     * Create players based on level data and profiles.
     *
     * @param profiles Profiles to initialise players from.
     */

    private void initiatePlayers(Profile[] profiles) {
        players = new Player[profiles.length];
        int[] spawnPoints = level.getSpawnPoints();
        for (int i = 0; i < profiles.length; i++) {
            players[i] = new Player(profiles[i], spawnPoints[i * 2], spawnPoints[(i * 2) + 1], new int[6],
                    new ArrayList<Tile>(), false, false);
        }
        level.setPlayerArray(this.players);
    }

    /**
     * @param board
     * @param rowSize
     * @param columnSize
     */
    private void populateBoard(Board board, int rowSize, int columnSize) {
        for (int x = 0; x < rowSize; x++) {
            for (int y = 0; y < columnSize; y++) {
                board.insertTile(x, y, level.getSilkBag().populateRandomBoardTiles());
            }
        }
    }

    /**
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
     * @param x
     * @param y
     * @param player
     */
    public void movePlayer(int x, int y, int player) {
        level.getBoardData().movePlayer(level.getPlayerData()[player].getPlayerCordX(), level.getPlayerData()[player].getPlayerCordY(),
                x, y);
        checkWin();
    }

    /**
     * @param x
     * @param y
     */
    public void playerPlaceIce(int x, int y) {
        level.getBoardData().setTilesFrozen(x, y);
    }

    /**
     * @param x
     * @param y
     */
    public void playerPlaceFire(int x, int y) {
        level.getBoardData().setTilesOnFire(x, y);
    }

    /**
     * @param player
     * @param x
     * @param y
     */
    public void playerPlaceDouble(int player, int x, int y) {
        movePlayer(x, y, player);
        movePlayer(x, y, player); //TODO need to change
    }

    /**
     * @param player
     */
    public void playerPlaceBack(int player) {
        level.getBoardData().backTrackPlayer(level.getPlayerData()[player].getProfileCordHistory(),
                level.getPlayerData()[player].getPlayerCordX(), level.getPlayerData()[player].getPlayerCordY());
    }

    /**
     * @param tile
     * @param player
     * @param x
     * @param y
     * @return
     */
    public Boolean checkWhichActionTile(ActionTile tile, int player, int x, int y) {
        if (tile instanceof FireTile) {
            if (checkActionCardValid(x, y)) {
                playerPlaceFire(x, y);
                return true;
            }
        } else if (tile instanceof IceTile) {
            if (checkActionCardValid(x, y)) {
                playerPlaceIce(x, y);
                return true;
            }
        } else if (tile instanceof DoubleMoveTile) {
            playerPlaceDouble(player, x, y);
            return true;
        } else {
            if (checkBackTrackValid(player)) {
                playerPlaceBack(player);
                return true;
            }
        }
        return false;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public Boolean checkActionCardValid(int x, int y) {

        for (int i = 0; i < level.getPlayerData().length; i++) {
            if (Arrays.equals(level.getBoardData().playerLocationOnBoard(x, y, level.getPlayerData()[i]),
                    new int[]{x, y})) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param player
     * @return
     */
    public Boolean checkBackTrackValid(int player) {
        return !level.getPlayerData()[player].getBackTrackCheck();

    }

    /**
     *
     */
    public void playerDraw(int player) {
        this.level.getSilkBag().giveTile(level.getPlayerData()[player]);
    }

    /**
     * TODO Connects with constructor
     * <p>
     * Managing Turns
     * <p>
     * STARTING PHASE
     * OPTIONAL - SAVE GAME
     * <p>
     * <p>
     * DRAWING PHASE - DRAW TILE
     * WAIT TILL PLAYER HAS DRAWN FROM SILK BAG
     * THEN PROCEED
     * <p>
     * <p>
     * OPTIONAL PHASE - PLACING TILE
     * Checks to see if action performed on player turn is legal
     * CHECK FOR ACTION TILES, AS WELL AS TILE PLACEMENTS
     * if legal, proceed
     * CHECK IF THEY WIN BEFORE PROCEEDING
     * else, cancel action.
     * <p>
     * MOVING PHASE (ACCOUNT FOR DOUBLE MOVE PLEASE!!!)
     * Takes keystrokes, and moves in that direction - Proceeds to END TURN PHASE
     * CHECKS FOR OBSTRUCTION
     * IF obstruction is detected FLAG TRUE
     * SHOWS END TURN BUTTON
     * <p>
     * <p>
     * ENDING TURN PHASE
     * Check to see if player has won.
     * If true
     * Increment player Win count
     * Increment OTHER player loss count
     * END GAME
     * IF SAVED GAME
     * DELETE SAVE FILE
     * If false
     * Next Player Turn
     * SETTING THIS player[x].isPlayerTurn to False
     * Set player[x + 1].isPlayerTurn to True
     * If player[x + 1] = 4
     * THEN player[0].isPlayerTurn to True
     **/

    public void checkPlayerTurn() {

        for (int i = 0; i < level.getPlayerData().length; i++) {
            if (players[i].getPlayerTurn() == true) {
                flow(i);
            }
        }
    }

    public void flow(int i) {
        boolean buttonFlag = false;
        boolean optionalButtonFlag = true;
        this.players = this.level.getPlayerData();
        // constructor which connects to deniz part here
        while (!checkWin()) {
            while (!buttonFlag) {
                if (getSaveButton() == true) {
                    saveGame();
                } else if (getDrawButton() == true) {
                    // This starts a player's turn
                    buttonFlag = true;
                }
            }
            playerDraw(i);
            while (this.players[i].getTileHand().getType() != null) {

                if ((level.getTempCardinal()) != Board.Cardinals.NULL) {
                    slotTiles(level.getTempCardinal(), this.players[i].getTileHand(), level.getTempX(),
                            level.getTempY());
                    optionalButtonFlag = true;
                    this.players[i].setTileHand(null);
                    level.setTempCardinal(Board.Cardinals.NULL);
                }
            }
            while (optionalButtonFlag) {
                if (actionTilePlaceFlag = true) {
                    if (onClickFlagTop = true) {

                    } else if (onClickFlagBottom = true) {

                    } else if (onClickFlagLeft = true) {

                    } else if (onClickFlagRight = true) {

                    } else {

                    }
                } else {
                    //how to use a key listener?
                    optionalButtonFlag = false;
                }
                if (checkWin()) {
                    declareWinner(i);
                } else {
                    players[i].playerTurn();
                    if (i == players.length - 1) {
                        i = 0;
                        players[i].playerTurn();
                    } else {
                        i++;
                        players[i].playerTurn();
                    }
                }
            }
        }
    }

    /**
     * Prepare the game to finish, either for saving or at a win.
     *
     * @return True if the game could end
     */
    public Boolean endGame() {
        return false;
    }

    public Boolean saveGameCheck() {
        //  In range of amount of levels in saved levels
        for (int i = 0; i < level.getSavedLevels().size(); i++) {
            //  If name is equal to a level in saved level.
            if (level.getSavedLevels().get(i).getBoardData().getNameOfBoard().equals
                    (this.level.getBoardData().getNameOfBoard())) {
                level.getSavedLevels().remove(i);
                level.getSavedLevels().add(this.level);
                return true;
            }
        }
        return false;
    }

    /**
     * Announces that a player has won.
     *
     * @return Player that won.
     */
    public void declareWinner(int i) {
        Player[] players = level.getPlayerData();
        for (int x = 0; x < players.length; x++) {
            if (players[x] == players[i]) {
                players[i].incPlayerWin();
            } else {
                players[i].incPlayerLoss();
            }
        }
    }

    public Boolean getDrawButton() {
        return drawButton;
    }

    public void setDrawButton(Boolean drawButton) {
        this.drawButton = drawButton;
    }
}