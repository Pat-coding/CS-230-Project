package backend;
import java.util.Random;
import Tiles.*;


/**
 *  SilkBag stores the tiles in the Silk Bag, gives out tiles, and takes takes into it.
 *
 *  @author Diana
 */
public class SilkBag {

    Random rand;
    private int[] silkBagContent;
    private FloorTile tempTile;

    /**
     *  Constructor for Silk Bag.
     *
     *  @param silkBagContent The
     */
    public SilkBag(int[] silkBagContent) {
        this.silkBagContent = silkBagContent;
    }

    /**
     *  Gives out tile to player.
     *
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
     *  generate random assortment of tiles when loading new game.
     *
     *  @return random tiles
     */
    public FloorTile populateRandomBoardTiles() {
        int rnd = new Random().nextInt(4);
        if (silkBagContent[rnd] < 1) {
            populateRandomBoardTiles();
        } else {
            silkBagContent[rnd] = silkBagContent[rnd] - 1;
            switch (rnd) {
                case 0:
                    tempTile = new StraightTile(randomOrientation(), "NORMAL", false);
                    break;
                case 1:
                    tempTile = new CornerTile(randomOrientation(), "NORMAL", false);
                    break;
                case 2:
                    tempTile = new TShapedTile(randomOrientation(), "NORMAL", false);
                    break;
                case 3:
                    tempTile = new GoalTile(randomOrientation(), "NORMAL", false);
                    break;
                default:
                    tempTile = null;
                    break;
            }
        }
        return tempTile;
    }

    /**
     *  Generate random orientation for tiles.
     *
     *  @return
     */
    public int randomOrientation() {
        int[] orientation = new int[]{0, 90, 180, 270};
        int rnd = new Random().nextInt(orientation.length);
        return orientation[rnd];
    }

    /**
     *  Getter for SilkBag content.
     *
     * @return
     */
    public int[] getSilkBagContent() {
        return silkBagContent;
    }

    /**
     *
     *
     * @param type
     */
    public void insertTileToBag(Tile type) {
        switch (type.getType()) {
            case "Straight":
                this.silkBagContent[0]++;
                break;
            case "Corner":
                this.silkBagContent[1]++;
                break;
            case "TShaped":
                this.silkBagContent[2]++;
                break;
            case "Goal":
                this.silkBagContent[3]++;
                break;
            case "Ice":
                this.silkBagContent[4]++;
                break;
            case "Backtrack":
                this.silkBagContent[5]++;
                break;
            case "DoubleMove":
                this.silkBagContent[6]++;
                break;
            case "Fire":
                this.silkBagContent[7]++;
        }
    }
}