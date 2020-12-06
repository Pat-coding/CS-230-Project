package backend;

import javafx.scene.control.Alert;

import java.util.ArrayList;

/**
 * this class handles game logic while players are in a game
 *
 * @author Ben Dodd
 * @version 1.0.0
 */


public class GameFlow {

    private static Player tempPlayer;
    private Level level;
    private Player[] player;
    private int playerIndex;
    private int gameTurn;
    private boolean hasDrawn;
    private Board board;
    private SilkBag silkBag;

    /**
     * This Constructor keeps the game running until it is won or saved.
     *
     * @param level       the current level being played
     * @param playerIndex players in current game
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

    /**
     * Gets profiles of users and assigns them to players.
     *
     * @param profiles profiles of users being played
     * @param level    current game level
     */

    public static void initiatePlayers(ArrayList<Profile> profiles, Level level) {
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


    /**
     * This method handles player movement on board and makes sure it is legal.
     *
     */
    public void movePlayerOnBoard() {
        int x = board.playerLocationOnBoard(player[playerIndex])[0];
        int y = board.playerLocationOnBoard(player[playerIndex])[1];
        if (level.pressUpFlag && !level.playerHasMovedFlag) {
            if (checkPlayerBounds(x, (y - 1)) && checkPlayerMovement(x, (y - 1), this.playerIndex)) {
                movePlayer(x, (y - 1));
                level.pressUpFlag = false;
                player[this.playerIndex].setPlayerCordY((y - 1));
                level.playerHasMovedFlag = true;
            }
        }

        if (level.pressDownFlag && !level.playerHasMovedFlag) {
            if (checkPlayerBounds(x, (y + 1)) && checkPlayerMovement(x, (y + 1), this.playerIndex)) {
                movePlayer(x, (y + 1));
                level.pressDownFlag = false;
                player[this.playerIndex].setPlayerCordY((y + 1));
                level.playerHasMovedFlag = true;
            }
        }

        if (level.pressLeftFlag && !level.playerHasMovedFlag) {
            if (checkPlayerBounds((x - 1), y) && checkPlayerMovement((x - 1), y, this.playerIndex)) {
                movePlayer((x - 1), y);
                level.pressLeftFlag = false;
                player[this.playerIndex].setPlayerCordX(x - 1);
                level.playerHasMovedFlag = true;
            }
        }

        if (level.pressRightFlag && !level.playerHasMovedFlag) {
            if (checkPlayerBounds((x + 1), y) && checkPlayerMovement((x + 1), y, this.playerIndex)) {
                movePlayer((x + 1), y);
                level.pressRightFlag = false;
                player[this.playerIndex].setPlayerCordX(x + 1);
                level.playerHasMovedFlag = true;
            }
        }

    }

    /**
     * Go to the next turn of the board.
     *
     */
    public void incPlayerTurn() {
        player[playerIndex].playerTurn();
        if (playerIndex == player.length - 1) {
            Level.setPlayerIndex(0);
        } else {
            Level.setPlayerIndex(playerIndex + 1);
        }
        playerIndex = Level.getPlayerIndex();
        player[this.playerIndex].playerTurn();
    }

    /**
     * This method finds the player location on board and stores it locally.
     *
     */
    public void updatePlayer() {
        for (int i = 0; i < player.length - 1; i++) {
            int x = board.playerLocationOnBoard(player[i])[0];
            int y = board.playerLocationOnBoard(player[i])[1];
            player[i].setPlayerCordX(x);
            player[i].setPlayerCordY(y);
        }
    }

    /**
     * This method checks if the player move is legal and returns if they can.
     *
     * @param x           coord player wants to move to
     * @param y           coord player wants to move to
     * @param playerIndex player wanting to move
     * @return true if move is legal
     */
    public boolean checkPlayerMovement(int x, int y, int playerIndex) {
        int px = board.playerLocationOnBoard(player[playerIndex])[0];
        int py = board.playerLocationOnBoard(player[playerIndex])[1];

        //  if the tile is out of bounds
        if (board.getTileFromBoard(x, y) == null) {
            return false;
        } else {
            if (x == px - 1) {
                //  if there are both false then that mean it returns true?
                return board.getTileFromBoard(px, py).isAccessFromLeft() &&
                        board.getTileFromBoard(x, y).isAccessFromRight();
            } else if (x == px + 1) {
                return board.getTileFromBoard(px, py).isAccessFromRight() &&
                        board.getTileFromBoard(x, y).isAccessFromLeft();
            } else if (y == py - 1) {
                return board.getTileFromBoard(px, py).isAccessFromTop() &&
                        board.getTileFromBoard(x, y).isAccessFromBottom();
            } else if (y == py + 1) {
                return board.getTileFromBoard(px, py).isAccessFromBottom() &&
                        board.getTileFromBoard(x, y).isAccessFromTop();
            }
        }
        return false;
    }

    /**
     * Moves the player to desired location
     *
     * @param x coordinate
     * @param y coordinate
     */
    public void movePlayer(int x, int y) {
        int px = board.playerLocationOnBoard(player[playerIndex])[0];
        int py = board.playerLocationOnBoard(player[playerIndex])[1];
        board.movePlayer(px, py, x, y);
        updatePlayer();
        checkWin();
    }

    private boolean checkPlayerBounds(int x, int y) {
        if ((x < 0)
                || (x > board.getRowSize() - 1)
                || (y < 0)
                || (y > board.getColumnSize() - 1)
                || (board.getPlayerFromBoard(x, y) != null)) {
            System.out.println("Player out of bounds");
            return false;
        } else {
            return true;
        }
    }


    /**
     * This method handles drawing and placing a tiles and makes sure game cant be saved part way through.
     *
     */
    public void flow() {
        updatePlayer();

        //  Check to see if the player is allowed to save the game.
        if (level.saveButtonFlag && !hasDrawn) {
            System.out.println("Player " + this.playerIndex + " has pressed the save game button!");
            saveGame();
            level.saveButtonFlag = false;
        }

        //  Allows the player to draw a tile.
        if (level.drawTileFlag && !hasDrawn) {
            hasDrawn = true;
            drawTile();
            level.drawTileFlag = false;
        }

        if (player[playerIndex].getTileHand() != null) {
            level.playerHandFlag = true;
        }

        //  Throws failed to save error
        if (level.saveButtonFlag && hasDrawn) {
            System.out.println("Player " + this.playerIndex + " has attempted to save the game after drawing!");
            level.saveButtonFlag = false;
        }


        //  Throws failed tile draw message
        if (level.drawTileFlag && hasDrawn) {
            System.out.println("Player " + this.playerIndex + " attempted to draw another tile!!!");
            level.drawTileFlag = false;
        }

        //  Throws multiple attempts of placing tile error
        if (player[this.playerIndex].getTileHand() == null && hasDrawn) {

            System.out.println("Player " + this.playerIndex + " has attempted placed another tile this turn!");
            level.setTempCardinal(null);
            level.setTempX(-1);
            level.setTempY(-1);
        }

        //  Throws error for not drawing a tile
        if (level.getTempCardinal() != null && !hasDrawn) {

            System.out.println("Player " + this.playerIndex + " needs to draw before slotting a tile!");
            level.setTempCardinal(null);
            level.setTempX(-1);
            level.setTempY(-1);
        }

        //  This means player has placed a tile.
        if (level.getTempCardinal() != null && level.getTempX() != -1
                && level.getTempY() != -1 && player[this.playerIndex].getTileHand() != null) {

            board.placeOnNewTile(level.getTempCardinal(), level.getTempX(), level.getTempY(),
                    player[this.playerIndex].getTileHand());

            System.out.println("Player " + this.playerIndex + " has slotted a tile in the board!");
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

        if (checkWin()) {
            declareWinner(this.playerIndex);
            endGame();
            winnerAlert();
        }

        if (board.getTileFromBoard(player[playerIndex].getPlayerCordX(),
                (player[playerIndex].getPlayerCordY())).getType().equals("Goal")) {
            declareWinner(this.playerIndex);
            endGame();
            winnerAlert();
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
                    (this.board.getNameOfBoard())) {
                Level.getSavedLevels().remove(i);

            }
        }
        exportGames();
    }

    /**
     * This methods overwrites any old save games of this instance.
     *
     */
    private void saveGame() {
        //  Override previous save game
        updatePlayer();
        if (!saveGameCheck()) {
            level.getSavedLevels().add(this.level);
        }
        exportGames();

    }

    /**
     * This saves the current game state to files.
     *
     */

    private void exportGames() {
        FileManager.createNewProfile(Level.getProfileArray());
        FileManager.createNewSaveFile(Level.getSavedLevels());
    }

    /**
     * This method draws a random tile from silk bag and adds it
     * to current players inventory.
     *
     */
    public void drawTile() {
        level.getSilkBag().giveTile(player[this.playerIndex]);
    }

    /**
     * Check if the board is in a state where a player has won.
     *
     * @return True if there is a winning situation.
     */
    public boolean checkWin() {
        int px = board.playerLocationOnBoard(player[playerIndex])[0];
        int py = board.playerLocationOnBoard(player[playerIndex])[1];
        if (board.getTileFromBoard(px, py).getType().equals("Goal")) {
            for (int i = 0; i < player.length; i++) {
                if (board.getTileFromBoard(px, py).getType().equals("Goal"))
                    declareWinner(i);
                return true;
            }
        }
        return false;
    }

    private void winnerAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player " + this.playerIndex + " has won!");
        alert.showAndWait();
        System.exit(404);
    }

    /**
     * Announces that a player has won the game.
     *
     * @param playerIndex index of winning player
     */
    public void declareWinner(int playerIndex) {
        for (int i = 0; i < player.length; i++) {
            if (player[i] == player[playerIndex]) {
                player[i].incPlayerWin();
            } else {
                player[i].incPlayerLoss();
            }
        }

    }

    /**
     * This method returns if the game saved correctly.
     *
     * @return true if saved correctly
     */
    public boolean saveGameCheck() {
        //  In range of amount of levels in saved levels
        for (int i = 0; i < level.getSavedLevels().size(); i++) {
            //  If name is equal to a level in saved level.
            if (level.getSavedLevels().get(i).getBoardData().getNameOfBoard().equals
                    (this.board.getNameOfBoard())) {
                level.getSavedLevels().remove(i);
                level.getSavedLevels().add(this.level);
                return true;
            }
        }
        return false;
    }

}
