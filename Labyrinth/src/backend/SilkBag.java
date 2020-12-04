package backend;
import java.util.Random;
import Tiles.*;

/**
 * @author Diana
 */

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
     * this method pulls a random tile from the silk bag and adds it to inventory
     * @param player
     */

    public void giveTile(Player player) {
        int rnd = new Random().nextInt(silkBagContent.length);

        if (silkBagContent[rnd] < 1) {
            giveTile(player);
        } else {
            silkBagContent[rnd] = silkBagContent[rnd] - 1;
            switch (rnd) {
                case 0:
                    FloorTile StraightTile = new StraightTile(randomOrientation(), "NORMAL", false);
                    player.setTileHand(StraightTile);
                    break;
                case 1:
                    FloorTile CornerTile = new CornerTile(randomOrientation(), "NORMAL", false);
                    player.setTileHand(CornerTile);
                    break;
                case 2:
                    FloorTile TShapedTile = new TShapedTile(randomOrientation(), "NORMAL", false);
                    player.setTileHand(TShapedTile);
                    break;
                case 3:
                    FloorTile GoalTile = new GoalTile(randomOrientation(), "NORMAL", false);
                    player.setTileHand(GoalTile);
                    break;
                case 4:
                    Tile IceTile = new IceTile();
                    player.getPlayerInventory().add(IceTile);
                    break;
                case 5:
                    Tile BackTrackTile = new BacktrackTile();
                    player.getPlayerInventory().add(BackTrackTile);
                    break;
                case 6:
                    Tile DoubleMoveTile = new DoubleMoveTile();
                    player.getPlayerInventory().add(DoubleMoveTile);
                    break;
                case 7:
                    Tile FireTile = new FireTile();
                    player.getPlayerInventory().add(FireTile);
                    break;
            }
        }
    }

    /**
     * this method pulls random tiles from silk bag and adds them to the board
     * @return random FloorTile
     */
    public FloorTile populateRandomBoardTiles() {
        int randomNum = rand.nextInt(silkBagContent.length);

        if (silkBagContent[randomNum] <= 0) {
            populateRandomBoardTiles();
        } else {
            silkBagContent[randomNum] = silkBagContent[randomNum] - 1;

            switch (randomNum) {
                case 0:
                    return new StraightTile(randomOrientation(), "NORMAL", false);
                case 1:
                    return new CornerTile(randomOrientation(), "NORMAL", false);
                case 2:
                    return new TShapedTile(randomOrientation(), "NORMAL", false);
                case 3:
                    return new GoalTile(randomOrientation(), "NORMAL", false);
            }
        }
        return null;
    }

    /**
     * this method returns a random oreintation for a tile
     * @return degrees in in for oreintation
     */
    public int randomOrientation() {
        int[] orientation = new int[]{0, 90, 180, 270};
        int rnd = new Random().nextInt(orientation.length);
        return orientation[rnd];
    }

    /**
     * this method gives contents of silk bag
     * @return array of ints from silkbag
     */
    public int[] getSilkBagContent() {
        return silkBagContent;
    }

    /**
     * this method takes a tile as an input and puts it back in silk bag
     * @param type the tile being inputted
     */
    public void insertTileToBag(Tile type) {

        switch (type.getType()) {
            case "StraightTile":
                this.silkBagContent[0]++;
                break;
            case "CornerTile":
                this.silkBagContent[1]++;
                break;
            case "TShapedTile":
                this.silkBagContent[2]++;
                break;
            case "GoalTile":
                this.silkBagContent[3]++;
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
            case "FireTile":
                this.silkBagContent[7]++;
        }
    }
}