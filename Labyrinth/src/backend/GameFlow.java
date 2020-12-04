package backend;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.ArrayList;

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
    private static Player tempPlayer;
    private Stage stage;


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

    public static void initiatePlayers(ArrayList<Profile> profiles, Level level) {
        System.out.println("profile size: " + profiles.size());
        Player[] tempPlayerArray = new Player[profiles.size()];
        int[] spawnPoints = level.getSpawnPoints();

        for (int i = 0; i < profiles.size(); i++) {
            if (i == 0) {
                tempPlayer = new Player(profiles.get(i), spawnPoints[i * 2], spawnPoints[(i * 2) + 1], new int[6],
                        new ArrayList<>(), false, true);
            } else {
                tempPlayer = new Player(profiles.get(i), spawnPoints[i * 2], spawnPoints[(i * 2) + 1], new int[6],
                        new ArrayList<>(), false, false);
            }
            tempPlayerArray[i] = tempPlayer;
            level.getBoardData().insertPlayer(spawnPoints[i * 2], spawnPoints[(i * 2) + 1], tempPlayer);
        }
        level.setPlayerArray(tempPlayerArray);
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

        //  Need to implement constrain to see if the player has moved or not.
        if (level.endTurnFlag) {
            hasDrawn = false;
            level.endTurnFlag = false;
            incPlayerTurn();
        }

        if (level.playerHasMovedFlag) {
            if (checkWin()) {
                declareWinner(playerIndex);
                winnerAlert();
                endGame();
                //level.playerWinFlag = true;
            } else {
                incPlayerTurn();
            }
        }
    }


    public void movePlayerOnBoard() {
        int x = level.getPlayerData()[playerIndex].getPlayerCordX();
        int y = level.getPlayerData()[playerIndex].getPlayerCordY();
        if(level.pressUpFlag && !level.playerHasMovedFlag) {
            if(checkPlayerBounds(x, (y - 1)) && checkPlayerMovement(x,(y - 1), playerIndex)) {
                movePlayer(x, (y - 1), playerIndex);
                level.pressUpFlag = false;
                level.getPlayerData()[playerIndex].setPlayerCordY((y - 1));
                level.playerHasMovedFlag = true;
            }
        }

        if(level.pressDownFlag && !level.playerHasMovedFlag) {
            if(checkPlayerBounds(x, (y + 1)) && checkPlayerMovement(x, (y + 1), playerIndex)) {
                movePlayer(x, (y + 1), playerIndex);
                level.pressDownFlag = false;
                level.getPlayerData()[playerIndex].setPlayerCordY((y + 1));
                level.playerHasMovedFlag = true;
            }
        }

        if(level.pressLeftFlag && !level.playerHasMovedFlag) {
            if(checkPlayerBounds((x - 1), y) && checkPlayerMovement((x - 1), y, playerIndex)) {
                movePlayer((x - 1), y, playerIndex);
                level.pressLeftFlag = false;
                level.getPlayerData()[playerIndex].setPlayerCordX(x - 1);
                level.playerHasMovedFlag = true;
            }
        }

        if(level.pressRightFlag && !level.playerHasMovedFlag) {
            if(checkPlayerBounds((x + 1), y) && checkPlayerMovement((x + 1), y, playerIndex)) {
                movePlayer((x + 1), y, playerIndex);
                level.pressRightFlag = false;
                level.getPlayerData()[playerIndex].setPlayerCordX(x + 1);
                level.playerHasMovedFlag = true;
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
        System.out.println("This player before switching" + player[playerIndex].getPlayerTurn());
        player[playerIndex].playerTurn(); // set current players turn to false
        System.out.println("This player after switching" + player[playerIndex].getPlayerTurn());
        playerIndex ++; // increment which players turn it is
        if (playerIndex <= player.length) { // loop back to first player if at end of player array
            playerIndex = 0;
        }
        player[playerIndex].playerTurn(); // set next players turn to true
    }

    public void updatePlayer() {
        for (int i = 0; i < player.length - 1; i++) {
            int x = level.getBoardData().playerLocationOnBoard(player[i])[0];
            int y = level.getBoardData().playerLocationOnBoard(player[i])[1];
            player[i].setPlayerCordX(x);
            player[i].setPlayerCordY(y);
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

    public void saveGame() {
        //  Override previous save game
        updatePlayer();
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
        int px = player[playerIndex].getPlayerCordX();
        int py = player[playerIndex].getPlayerCordY();

        //  if the tile is out of bounds
        if(board.getTileFromBoard(x, y) == null) {
            return false;
        } else {
            if(x == px - 1) {
                //  if there are both false then that mean it returns true?
                return board.getTileFromBoard(px, py).isAccessFromLeft() &&
                        board.getTileFromBoard(x, y).isAccessFromRight();
            } else if(x == px + 1) {
                return board.getTileFromBoard(px, py).isAccessFromRight() &&
                        board.getTileFromBoard(x, y).isAccessFromLeft();
            } else if(y == py - 1) {
                return board.getTileFromBoard(px, py).isAccessFromTop() &&
                        board.getTileFromBoard(x, y).isAccessFromBottom();
            } else if(y == py + 1) {
                return board.getTileFromBoard(px, py).isAccessFromBottom() &&
                        board.getTileFromBoard(x, y).isAccessFromTop();
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

    private boolean checkPlayerBounds(int x, int y) {
        if((x < 0) || (x > level.getBoardData().getRowSize() - 1)|| (y < 0) || (y > level.getBoardData().getColumnSize() - 1) || (level.getBoardData().getPlayerFromBoard(x, y) != null)) {
            System.out.println("Player out of bounds");
            return false;
        } else {
            return true;
        }
    }

    private void winnerAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player " + playerIndex + " has won!");
        alert.showAndWait();
        System.exit(404);
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
        System.out.println("player " + i + " has won!");
    }
}
