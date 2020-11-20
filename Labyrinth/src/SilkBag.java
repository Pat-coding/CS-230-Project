
import java.util.Random;

import jdk.javadoc.internal.doclets.formats.html.markup.StringContent;

public class SilkBag {

    Random rand;
    private int[] silkBagContent;

    /**
     *
     * @param silkBagContent
     *  0th = Straight
     *  1st =
     *
     */


    public SilkBag(int[] silkBagContent) {
        this.silkBagLength = silkBagContent;
//        this.straight = silkBagContent[0];
//        this.corner = silkBagContent[1];
//        this.tShaped = silkBagContent[2];
//        this.fire = silkBagContent[3];
//        this.ice = silkBagContent[4];
//        this.backtrack = silkBagContent[5];
//        this.doublemove = silkBagContent[6];
    }
    //throws exception
    //create a new exception class that extends the default exception
    // throws NumberNotMatchingTile expection


    /**
     *
     * @param player
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
    