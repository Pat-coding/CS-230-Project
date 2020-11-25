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

    public void addToVisited() {}

    /**
     * This Method returns the players inventory
     *
     * @return Tiles held by player
     */
    public ArrayList<Tile> getPlayerInventory() {
        return playerInventory;
    }

    /**
     * This Method returns if it is the players turn
     *
     * @return boolean result for player turn
     */
    public boolean getPlayerTurn(){
        return  isPlayerTurn;
    }


    /**
     * This Method returns the players previous coordinates
     *
     * @return a array with the players previous x and y coords
     */

    public int[] getPrevCoordinates(){
        return new int[]{tilesVisitedX.get(tilesVisitedX.size()),tilesVisitedY.get(tilesVisitedY.size()-1)};

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
     * @param wincorords
     *
     */

    public void incPlayerWin(int[] wincorords){
        if (getPrevCoordinates() == wincorords)
            name.incrementWinCount();


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

