package backend;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Tiles.FloorTile;
import Tiles.Tile;
/**
 * @author Kane
 * @version 1.0
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

    public Player(Profile profile, int playerCordX,int playerCordY,int[] profileCordHistory,
                  ArrayList<Tile> playerInventory, boolean backTrackCheck, boolean isPlayerTurn){
        this.profile = profile;
        this.playerCordX = playerCordX;
        this.playerCordY = playerCordY;
        this.profileCordHistory = profileCordHistory;
        this.playerInventory = playerInventory;
        this.backTrackCheck = backTrackCheck;
        this.isPlayerTurn = isPlayerTurn;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public int getPlayerCordX() {
        return this.playerCordX;
    }

    public int getPlayerCordY() {
        return this.playerCordY;
    }

    public int[] getProfileCordHistory() {
        return this.profileCordHistory;
    }

    public ArrayList<Tile> getPlayerInventory() {
        return this.playerInventory;
    }

    public boolean getBackTrackCheck() {
        return this.backTrackCheck;
    }

    public boolean getPlayerTurn() {
        return this.isPlayerTurn;
    }

    /**
     * This Method check if player is at win coords and if so increments that players win stat
     *
     */

    public void incPlayerWin() {
        profile.incrementWinCount();
    }

    public void incPlayerLoss() {
        profile.incrementLoseCount();
    }

    /**
     * This Method flips player turn after they have finished their turn
     *
     * @return Boolean result
     */
    public void playerTurn(){
        isPlayerTurn = !isPlayerTurn;
    }


    public FloorTile getTileHand() {
        return tileHand;
    }

    public void setTileHand(FloorTile tileHand) {
        this.tileHand = tileHand;
    }
}
