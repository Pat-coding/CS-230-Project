package backend;


import java.util.Random;

import Tiles.*;

public class SilkBag {

    Random rand;
    private int[] silkBagContent;

    /**
     * @param silkBagContent 0th = Straight
     *                       1st =
     */


    public SilkBag(int[] silkBagContent) {
        this.silkBagContent = silkBagContent;
    }

    /**
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
                    Tile StraightTile = new StraightTile(randomOrientation(), "NORMAL", false);
                    player.getPlayerInventory().add(StraightTile);
                case 1:
                    Tile CornerTile = new CornerTile(randomOrientation(), "NORMAL", false);
                    player.getPlayerInventory().add(CornerTile);
                case 2:
                    Tile TShapedTile = new TShapedTile(randomOrientation(), "NORMAL", false);
                    player.getPlayerInventory().add(TShapedTile);
                case 3:
                    Tile GoalTile = new GoalTile(randomOrientation(), "NORMAL", false);
                    player.getPlayerInventory().add(GoalTile);
                case 4:
                    Tile IceTile = new IceTile();
                    player.getPlayerInventory().add(IceTile);
                case 5:
                    Tile BackTrackTile = new BacktrackTile();
                    player.getPlayerInventory().add(BackTrackTile);
                case 6:
                    Tile DoubleMoveTile = new DoubleMoveTile();
                    player.getPlayerInventory().add(DoubleMoveTile);
                case 7:
                    Tile FireTile = new FireTile();
                    player.getPlayerInventory().add(FireTile);

            }
        }
    }


    public int randomOrientation() {
        int[] orientation = new int[]{0, 90, 180, 270};
        int rnd = new Random().nextInt(orientation.length);
        return orientation[rnd];
    }

    public int[] getSilkBagContent() {
        return silkBagContent;
    }

    public void insertTileToBag(String type) {
        switch (type) {
            case "Straight":
                this.silkBagContent[0]++;
                break;
            case "CornerTile":
                this.silkBagContent[1]++;
                break;
            case "TShapedTile":
                this.silkBagContent[2]++;
                break;
            case "FireTile":
                this.silkBagContent[7]++;
                break;
            case "IceTile":
                this.silkBagContent[4]++;
                break;
            case "BacktrackTile":
                this.silkBagContent[5]++;
                break;
            case "DoubleMoveTile":
                this.silkBagContent[6]++;
                break;
            case "GoalTile":
                this.silkBagContent[3]++;
        }
    }
}