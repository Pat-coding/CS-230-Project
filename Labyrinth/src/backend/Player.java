package backend;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Tiles.FloorTile;
import Tiles.Tile;
/**
 * this class Stpres information about they player in the current game
 * @author Kane
 * @version 1.0
 */


/**
 * A model a player. Each player has a profile, coordinates,
 * inventory, coordinate history, history and a turn
 */
public class Player {

    private Profile profile;
    private int playerCordX;
    private int playerCordY;
    private int[] profileCordHistory;
    private ArrayList<Tile> playerInventory;
    private boolean backTrackCheck;
    private boolean isPlayerTurn;
    private FloorTile tileHand;

    /**
     * Player instance constructor
     *
     * @param profile
     * @param playerCordX
     * @param playerCordY
     * @param profileCordHistory
     * @param playerInventory
     * @param backTrackCheck
     * @param isPlayerTurn
     */
    public Player(Profile profile, int playerCordX, int playerCordY, int[] profileCordHistory,
                  ArrayList<Tile> playerInventory, boolean backTrackCheck, boolean isPlayerTurn) {
        this.profile = profile;
        this.playerCordX = playerCordX;
        this.playerCordY = playerCordY;
        this.profileCordHistory = profileCordHistory;
        this.playerInventory = playerInventory;
        this.backTrackCheck = backTrackCheck;
        this.isPlayerTurn = isPlayerTurn;
    }

    /**
     * Getter for the profile
     *
     * @return profile
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * Getter for the player's X coordinate
     *
     * @return X coordinate
     */
    public int getPlayerCordX() {
        return this.playerCordX;
    }

    /**
     * Sets the location of the Player's current x position
     *
     * @param x position
     */

    public void setPlayerCordX(int x) {
        this.playerCordX = x;
    }

    /**
     * Getter for the player's Y coordinate
     *
     * @return Y coordinate
     */
    public int getPlayerCordY() {
        return this.playerCordY;
    }

    /**
     * Sets the location of the Player's current y position
     *
     * @param y position
     */

    public void setPlayerCordY(int y) {
        this.playerCordY = y;
    }

    /**
     * Getter for the player's coordinate history
     *
     * @return coordinate history
     */
    public int[] getProfileCordHistory() {
        return this.profileCordHistory;
    }

    /**
     * Getter for the player's inventory
     *
     * @return player's inventory
     */
    public ArrayList<Tile> getPlayerInventory() {
        return this.playerInventory;
    }

    /**
     * Getter the back track check
     *
     * @return back track check
     */
    public boolean getBackTrackCheck() {
        return this.backTrackCheck;
    }

    /**
     * Gets the player's turn
     *
     * @return True if it's the player's turn
     */
    public boolean getPlayerTurn() {
        return this.isPlayerTurn;
    }

    /**
     * Checks if player is at win coords and
     * if so increments that players win stat
     */
    public void incPlayerWin() {
        for (int i = 0; i < Level.getProfileArray().size(); i++) {
            if (Level.getProfileArray().get(i).getProfileName().equals(this.profile.getProfileName())) {
                System.out.println(Level.getProfileArray().get(i));
                Level.getProfileArray().get(i).incrementWinCount();
                System.out.println(Level.getProfileArray().get(i).getProfileName() + " "
                        + Level.getProfileArray().get(i).getWinCount());
            }
        }
    }

    /**
     * Increments players loss stat if the
     * player isn't on win coords
     */
    public void incPlayerLoss() {
        for (int i = 0; i < Level.getProfileArray().size(); i++) {
            if (Level.getProfileArray().get(i).getProfileName().equals(this.profile.getProfileName())) {
                Level.getProfileArray().get(i).incrementLoseCount();
            }
        }
    }

    /**
     * This Method flips player turn after they have finished their turn
     *
     * @return Boolean result
     */
    public void playerTurn() {
        this.isPlayerTurn = !this.isPlayerTurn;
    }

    /**
     * Getter for the tile's in hand
     *
     * @return tile's in hand
     */
    public FloorTile getTileHand() {
        return tileHand;
    }

    /**
     * Setter for tile hand
     *
     * @param tileHand
     */
    public void setTileHand(FloorTile tileHand) {
        this.tileHand = tileHand;
    }
}
