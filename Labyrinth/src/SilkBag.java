/**
 * @author Diana 
 * @version 1.0
 */
import java.util.Random;

import jdk.javadoc.internal.doclets.formats.html.markup.StringContent;

public class SilkBag {

    Random rand;
    private int[] silkBagContent;
    private int straight;
    private int corner;
    private int tShaped;
    private int fire;
    private int ice;
    private int backtrack;
    private int doublemove;


    /**
<<<<<<< Updated upstream
     *
     * @param silkBagContent
     *  0th = Straight
     *  1st =
     *
=======
     * This is the constructor for the Silk Bag
     * @param silkBagContent is an integer array. 
     * Each position within the array is related to some type of tile (Ex. 1 is straight tile)
     * Each integer within the array indicates how many tiles of that type the Silk Bag contains                    
>>>>>>> Stashed changes
     */


    public SilkBag(int[] silkBagContent) {
<<<<<<< Updated upstream
        this.silkBagLength = silkBagContent;
//        this.straight = silkBagContent[0];
//        this.corner = silkBagContent[1];
//        this.tShaped = silkBagContent[2];
//        this.fire = silkBagContent[3];
//        this.ice = silkBagContent[4];
//        this.backtrack = silkBagContent[5];
//        this.doublemove = silkBagContent[6];
=======
        this.silkBagContent = silkBagContent;
       this.straight = silkBagContent[0];
       this.corner = silkBagContent[1];
       this.tShaped = silkBagContent[2];
       this.fire = silkBagContent[3];
       this.ice = silkBagContent[4];
       this.backtrack = silkBagContent[5];
       this.doublemove = silkBagContent[6];
>>>>>>> Stashed changes
    }
    //throws exception
    //create a new exception class that extends the default exception
    // throws NumberNotMatchingTile expection


<<<<<<< Updated upstream
    /**
     *
     * @param player
=======
    /** This method adds a random tile to the player inventory 
     * 
     * @param player indicates the player to whose inventory the tile will be added to
>>>>>>> Stashed changes
     */
    public void giveTile(Player player) {
        int randomNum = rand.nextInt(silkBagContent.length);

        if (silkBagContent[randomNum] <= 0) {
            giveTile(player);
        } else {
            silkBagContent[randomNum] = silkBagContent[randomNum] - 1;
            switch (randomNum) {
                case 0:
                    Tile StraightTile = new StraightTile(randomNum, null);
                    player.getPlayerInventory().add(StraightTile);
                case 1:
                    Tile CornerTile = new CornerTile(randomNum, null);
                    player.getPlayerInventory().add(CornerTile);
                case 2:
                    Tile TShapedTile = new TShapedTile(randomNum, null);
                    player.getPlayerInventory().add(TShapedTile);
                case 3:
                    Tile FireTile = new FireTile(randomNum, null);
                    player.getPlayerInventory().add(FireTile);
                case 4:
                    Tile IceTile = new IceTile(randomNum, null);
                    player.getPlayerInventory().add(IceTile);
                case 5:
                    Tile BackTrackTile = new BacktrackTile(randomNum, null);
                    player.getPlayerInventory().add(BackTrackTile);
                case 6:
                    Tile DoubleMoveTile = new DoubleMoveTile(randomNum, null);
                    player.getPlayerInventory().add(DoubleMoveTile);

            }
        }
    }

<<<<<<< Updated upstream


=======
    /**
     * This method will create a random orientation for the tile
     * 
     * @return integer that indicates the angle
     */
    public int randomOrientation() {
        int[] orientation = new int[]{0, 90, 180, 270};
        int rnd = new Random().nextInt(orientation.length);
        return orientation[rnd];
    }

    /**
     * This method will insert the discarded tile to the Silk Bag
     * 
     * @param type is to determine the type of tile that needs to be discarded to the bag
     */
>>>>>>> Stashed changes
    public void insertTileToBag(String type) {
        switch (type) {
            case "Straight":
                this.straight++;
                break;
            case "Corner":
                this.corner++;
                break;
            case "TShaped":
                this.Tshaped
        }

        this.silkBagContent[tileType]++;
    