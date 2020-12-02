package backend;
import Tiles.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Ben Dodd
 * @version 1.0.0
 */

public class GameFlow {
    private Level level;
    private Player[] players;
    private int gameTurn;
    private boolean drawButton;
    private int playerTurn;

    /**
     * New Game
     *
     * @param level    Level to play.
     * @param profiles Profiles to create players from.
     */
    public GameFlow(Level level, Profile[] profiles) {
        this.level = level;
        this.initiatePlayers(profiles);
        this.gameTurn = level.getGameTurnData();
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
        this.gameTurn = level.getGameTurnData();
        this.players = this.level.getPlayerData();
    }

    public void saveGame() {
        //  Override previous save game
        if (!saveGameCheck()) {
            Level.getSavedLevels().add(this.level);
        }
    }

    /**
     * Check if the board is in a state where a player has won.
     *
     * @return True if there is a winning situation.
     */
    public boolean checkWin() {

        if (level.getBoardData().getPlayerFromBoard(level.getBoardData().getGoal()[0],
                level.getBoardData().getGoal()[1]) != null) {
            for(int i = 0; i < players.length; i++) {
                if(players[i] == level.getBoardData().getPlayerFromBoard(level.getBoardData().getGoal()[0],
                        level.getBoardData().getGoal()[1]))
                    declareWinner(i);
                return true;
            }
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
    public boolean checkWhichActionTile(ActionTile tile, int player, int x, int y) {
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
    public boolean checkActionCardValid(int x, int y) {

        for (int i = 0; i < level.getPlayerData().length; i++) {
            if (Arrays.equals(level.getBoardData().playerLocationOnBoard(level.getPlayerData()[i]),
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
    public boolean checkBackTrackValid(int player) {
        return !level.getPlayerData()[player].getBackTrackCheck();

    }

    /**
     *
     */
    public void playerDraw(int player) {
        this.level.getSilkBag().giveTile(level.getPlayerData()[player]);
    }

    public void checkPlayerTurn() {

        for (int i = 0; i < level.getPlayerData().length; i++) {
            if (players[i].getPlayerTurn()) {
                flow(i);
            }
        }
    }

    public boolean checkPlayerMovement(int x, int y, int playerNum) {
        int px = players[playerNum].getPlayerCordX();
        int py = players[playerNum].getPlayerCordY();

        if(level.getBoardData().getTileFromBoard(x, y) == null) {
            return false;
        } else {
            if(x == px - 1) {
                return level.getBoardData().getTileFromBoard(px, py).isAccessFromLeft() ==
                        level.getBoardData().getTileFromBoard(x, y).isAccessFromRight();
            } else if(x == px + 1) {
                return level.getBoardData().getTileFromBoard(px, py).isAccessFromRight() ==
                        level.getBoardData().getTileFromBoard(x, y).isAccessFromLeft();
            } else if(y == py - 1) {
                return level.getBoardData().getTileFromBoard(px, py).isAccessFromTop() ==
                        level.getBoardData().getTileFromBoard(x, y).isAccessFromBottom();
            } else if(y == py + 1) {
                return level.getBoardData().getTileFromBoard(px, py).isAccessFromBottom() ==
                        level.getBoardData().getTileFromBoard(x, y).isAccessFromTop();
            }
        }
        return false;
    }

    /**
     * Go to the next turn of the board.
     */
    public void incPlayerTurn() {
        // set the next player's turn to true (playerTurn method)
        // set the previous player's turn to false (playerTurn method)
        this.players[this.playerTurn].playerTurn(); // set current players turn to false
        this.playerTurn ++; // increment which players turn it is
        if (this.playerTurn == this.players.length) { // loop back to first player if at end of player array
            this.playerTurn = 0;
        }
        this.players[this.playerTurn].playerTurn(); // set next players turn to true
    }

    /**
     * Regulates the turn of the players
     *
     * @param playerIndex
     */
    public void flow(int playerIndex) {

        this.players = this.level.getPlayerData();
        while (checkWin() == false) {
            //  This loop forces the player to draw a Tile at the start of a turn.
            while (level.wantToSaveOpportunityFlag == false) {
                //  The player is given an opportunity to save the game here.
                if (level.saveButtonFlag == true) {
                    saveGame();
                    level.saveButtonFlag = false;

                } else if (level.drawTileFlag == true) { // The player losses this opportunity if they press drawTileButton
                    // This starts a player's turn
                    playerDraw(playerIndex);
                    level.drawTileFlag = false;
                    level.wantToSaveOpportunityFlag = true;

                    //  Increment game turn
                    if (playerIndex == 0) {
                        gameTurn++;
                    }
                }
            }


            while (this.players[playerIndex].getTileHand().getType() != null) {
                if (level.arrowFlagPressedVert = true) {
                    if (level.getBoardData().checkTileInsertionRow(level.getTempY()) == true) {
                        slotTiles(level.getTempCardinal(), this.players[playerIndex].getTileHand(), level.getTempX(),
                                level.getTempY());
                        this.players[playerIndex].setTileHand(null);
                        level.setTempCardinal(Board.Cardinals.NULL);

                    }
                    level.arrowFlagPressedVert = false;

                } else if (level.arrowFlagPressedHorz = true) {
                    if (level.getBoardData().checkTileInsertionCol(level.getTempX()) == true) {
                        slotTiles(level.getTempCardinal(), this.players[playerIndex].getTileHand(), level.getTempX(),
                                level.getTempY());
                        this.players[playerIndex].setTileHand(null);
                        level.setTempCardinal(Board.Cardinals.NULL);
                    }
                    level.arrowFlagPressedHorz = false;
                }
            }

            //  We can add action tiles here once we get the game working. at the moment, we are not implementing them
            //  Movement will be done in the front end, through a series of buttons. Unfortunate, but no choice now.

            while (level.movementFlag = true) {

                if (level.pressUpFlag == true) {

                    if (checkPlayerMovement(level.getTempX(), level.getTempY(), playerIndex) == true) {
                        level.getBoardData().movePlayer(players[playerIndex].getPlayerCordX(),
                                players[playerIndex].getPlayerCordY(), level.getTempX(), level.getTempY());
                        level.movementFlag = false;
                    }
                    level.pressUpFlag = false;

                } else if (level.pressDownFlag == true) {

                    if (checkPlayerMovement(level.getTempX(), level.getTempY(), playerIndex) == true) {
                        level.getBoardData().movePlayer(players[playerIndex].getPlayerCordX(),
                                players[playerIndex].getPlayerCordY(), level.getTempX(), level.getTempY());
                        level.movementFlag = false;
                    }
                    level.pressDownFlag = false;

                } else if (level.pressRightFlag == true) {

                    if (checkPlayerMovement(level.getTempX(), level.getTempY(), playerIndex) == true) {
                        level.getBoardData().movePlayer(players[playerIndex].getPlayerCordX(), players[playerIndex].getPlayerCordY(),
                                level.getTempX(), level.getTempY());
                        level.movementFlag = false;
                    }
                    level.pressRightFlag = false;
                } else if (level.pressLeftFlag == true) {

                    if (checkPlayerMovement(level.getTempX(), level.getTempY(), playerIndex) == true) {
                        level.getBoardData().movePlayer(players[playerIndex].getPlayerCordX(), players[playerIndex].getPlayerCordY(),
                                level.getTempX(), level.getTempY());
                        level.movementFlag = false;
                    }
                    level.pressLeftFlag = false;
                }

            }


            if (checkWin() == true) {
                declareWinner(playerIndex);
                endGame();
            } else {
                incPlayerTurn();
            }

        }
    }


    /**
     * Prepare the game to finish, either for saving or at a win.
     *
     * @return True if the game could end
     */
    public void endGame() {
        for (int i = 0; i < Level.getSavedLevels().size(); i++) {
            //  If name is equal to a level in saved level.
            if (Level.getSavedLevels().get(i).getBoardData().getNameOfBoard().equals
                    (this.level.getBoardData().getNameOfBoard())) {
                Level.getSavedLevels().remove(i);
                exportGames();
            }
        }
    }

    public void exportGames() {
        FileManager.createNewProfile(Level.getProfileArray());
        FileManager.createNewSaveFile(Level.getSavedLevels());
    }

    public boolean saveGameCheck() {
        //  In range of amount of levels in saved levels
        for (int i = 0; i < Level.getSavedLevels().size(); i++) {
            //  If name is equal to a level in saved level.
            if (Level.getSavedLevels().get(i).getBoardData().getNameOfBoard().equals
                    (this.level.getBoardData().getNameOfBoard())) {
                Level.getSavedLevels().remove(i);
                Level.getSavedLevels().add(this.level);
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

}
