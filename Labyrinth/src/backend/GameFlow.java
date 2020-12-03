package backend;
import Tiles.*;
import layout.BoardController;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Ben Dodd
 * @version 1.0.0
 */

public class GameFlow {

    private Level level;
    private Player[] player;
    private int playerIndex;
    private int gameTurn;
    private boolean hasDrawn;
    private Board board;
    private SilkBag silkBag;


    /**
     * Continue Level
     *
     * @param level Level to play.
     */

    public GameFlow(Level level, int playerIndex) {
        this.level = level;
        this.player = level.getPlayerData();
        this.gameTurn = level.getGameTurnData();
        this.playerIndex = playerIndex;
        this.hasDrawn = false;
        this.board = level.getBoardData();
        this.silkBag = level.getSilkBag();
    }

    public void flow() {
        for(int x = 0; x < level.getBoardData().getRowSize(); x++) {
            for(int y = 0; y < level.getBoardData().getColumnSize(); y++) {
                System.out.println(level.getBoardData().getPlayerFromBoard(x, y));
            }
        }


        //  Check to see if the player is allowed to save the game.
        if (level.saveButtonFlag && !hasDrawn) {
            System.out.println("Player " + playerIndex + " has pressed the save game button!");
            saveGame();
            level.saveButtonFlag = false;
        }

        //  Allows the player to draw a tile.
        if (level.drawTileFlag && !hasDrawn) {
            hasDrawn = true;
            drawTile();
            System.out.println("Player " + playerIndex + " has drawn the " +
                    player[playerIndex].getTileHand() + " tile!");
            System.out.println("Player " + playerIndex + " has drawn the " +
                    player[playerIndex].getPlayerInventory());
            level.drawTileFlag = false;
        }

        //  Throws failed to save error
        if (level.saveButtonFlag && hasDrawn) {
            System.out.println("Player " + playerIndex + " has attempted to save the game after drawing!");
            level.saveButtonFlag = false;
        }


        //  Throws failed tile draw message
        if (level.drawTileFlag && hasDrawn) {
            System.out.println("Player " + playerIndex + " attempted to draw another tile!!!");
            level.drawTileFlag = false;
        }

        //  Throws multiple attempts of placing tile error
        if (player[playerIndex].getTileHand() == null && hasDrawn) {

            System.out.println("Player " + playerIndex + " has attempted placed another tile this turn!");
            level.setTempCardinal(null);
            level.setTempX(-1);
            level.setTempY(-1);
        }

        //  Throws error for not drawing a tile
        if (level.getTempCardinal() != null && !hasDrawn) {

            System.out.println("Player " + playerIndex + " needs to draw before slotting a tile!");
            level.setTempCardinal(null);
            level.setTempX(-1);
            level.setTempY(-1);
        }


        //  This means player has placed a tile.
        if (level.getTempCardinal() != null && level.getTempX() != -1
                && level.getTempY() != -1 && player[playerIndex].getTileHand() != null) {

            if (level.getTempCardinal() == Board.Cardinals.LEFT ||
                    level.getTempCardinal() == Board.Cardinals.RIGHT) {

                board.placeOnNewTile(level.getTempCardinal(), level.getTempX(), level.getTempY()
                ,player[playerIndex].getTileHand());


            } else {

                board.placeOnNewTile(level.getTempCardinal(), level.getTempX(), level.getTempY()
                        ,player[playerIndex].getTileHand());

            }

            System.out.println("Player " + playerIndex + " has slotted a tile in the board!");
            level.setTempCardinal(null);
            level.setTempX(-1);
            level.setTempY(-1);
        }

        if(level.pressUpFlag) {
            System.out.println("B: " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[0] + " " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[1]);
            movePlayer(level.getPlayerData()[playerIndex].getPlayerCordX(), (level.getPlayerData()[playerIndex].getPlayerCordX() + 1), playerIndex);
            level.pressUpFlag = false;
            System.out.println("A: " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[0] + " " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[1]);
        }

        if(level.pressDownFlag) {
            System.out.println("B " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[0] + " " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[1]);
            movePlayer(level.getPlayerData()[playerIndex].getPlayerCordX(), (level.getPlayerData()[playerIndex].getPlayerCordX() - 1), playerIndex);
            level.pressDownFlag = false;
            System.out.println("A " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[0] + " " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[1]);
        }

        if(level.pressLeftFlag) {
            System.out.println("B " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[0] + " " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[1]);
            movePlayer(level.getPlayerData()[playerIndex].getPlayerCordX() - 1, level.getPlayerData()[playerIndex].getPlayerCordX(), playerIndex);
            level.pressLeftFlag = false;
            System.out.println("A " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[0] + " " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[1]);
        }

        if(level.pressRightFlag) {
            System.out.println("B " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[0] + " " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[1]);
            movePlayer(level.getPlayerData()[playerIndex].getPlayerCordX() + 1, level.getPlayerData()[playerIndex].getPlayerCordX(), playerIndex);
            level.pressRightFlag = false;
            System.out.println("A " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[0] + " " + level.getBoardData().playerLocationOnBoard(player[playerIndex])[1]);
        }
        //  Need to implement constrain to see if the player has moved or not.
        if (level.endTurnFlag) {
            hasDrawn = false;
            level.endTurnFlag = false;
            incPlayerTurn();
        }

        if (level.playerHasMovedFlag) {
            if (checkWin()) {
                declareWinner(playerIndex);
                endGame();
            } else {
                incPlayerTurn();
            }
        }
    }



    public void drawTile() {
        level.getSilkBag().giveTile(player[playerIndex]);
    }



    /**
     * Check if the board is in a state where a player has won.
     *
     * @return True if there is a winning situation.
     */
    public boolean checkWin() {
        if (level.getBoardData().getPlayerFromBoard(level.getBoardData().getGoal()[0],
                level.getBoardData().getGoal()[1]) != null) {
            for(int i = 0; i < player.length; i++) {
                if(player[i] == level.getBoardData().getPlayerFromBoard(level.getBoardData().getGoal()[0],
                        level.getBoardData().getGoal()[1]))
                    declareWinner(i);
                return true;
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
        this.player[this.playerIndex].playerTurn(); // set current players turn to false
        this.playerIndex ++; // increment which players turn it is
        if (this.playerIndex == this.player.length) { // loop back to first player if at end of player array
            this.playerIndex = 0;
        }
        this.player[this.playerIndex].playerTurn(); // set next players turn to true
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

    public void saveGame() {
        //  Override previous save game
        System.out.println("Saving Game : Stage 1");
        if (!saveGameCheck()) {
            System.out.println("Saving Game : Stage 2");
            level.getSavedLevels().add(this.level);
        }
        exportGames();

    }

    public boolean saveGameCheck() {
        System.out.println("Saving Game : Stage 3");
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

    public boolean checkPlayerMovement(int x, int y, int playerIndex) {
        int px = level.getPlayerData()[playerIndex].getPlayerCordX();
        int py = level.getPlayerData()[playerIndex].getPlayerCordY();

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
