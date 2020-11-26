import java.lang.reflect.Array;
import java.util.ArrayList;

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
    private ArrayList<Tile> playerInventory = new ArrayList<>();
    private boolean backTrackCheck;
    private boolean isPlayerTurn;

    public Player(Profile profile, int playerCordX,int playerCordY,int[] profileCordHistory,
                  ArrayList<Tile> playerInventory, boolean backTrackCheck, Boolean isPlayerTurn){
        this.profile = profile;
        this.playerCordX = playerCordX;
        this.playerCordY = playerCordY;
        this.profileCordHistory = profileCordHistory;
        this.playerInventory = playerInventory;
        this.backTrackCheck = backTrackCheck;
        this.isPlayerTurn = isPlayerTurn;
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

    public Boolean getBackTrackCheck() {
        return this.backTrackCheck;
    }

    public Boolean isPlayerTurn() {
        return this.isPlayerTurn;
    }


    /**
     * This Method adds a tile to the inventory
     * @param pickedTile
     *
     */

    public void getFromSilkBag(Tile pickedTile){
        playerInventory.add(pickedTile);
    }

    /**
     * This Method check if player is at win coords and if so increments that players win stat
     *
     */

    public void incPlayerWin() {
        profile.incrementWinCount();
    }

    /**
     * This Method flips player turn after they have finished their turn
     *
     * @return Boolean result
     */
    public void playerTurn(){
        isPlayerTurn = !isPlayerTurn;
    }

    /**
     * This Method returns the chosen tile from the inventory and removes it as its been used
     * @param index the place of the tile
     * @return the selected Tile
     */
    public Tile takeFromInventory(int index){
        if(index-1 > playerInventory.size()){
            System.out.println("out of bounds ");
            return null;
        }else
            {Tile x = playerInventory.get(index);
            playerInventory.remove(index);
            return x;
            }

    }
}

