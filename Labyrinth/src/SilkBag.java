
import java.util.Random;

import jdk.javadoc.internal.doclets.formats.html.markup.StringContent;

public class SilkBag {

    int fire;
    int ice;
    int straight;
    int corner;
    int tShaped;
    int backtrack;
    int doublemove;
    Random rand;
    private int[] silkBagContent;

    public SilkBag(int[] silkBagContent) {
        this.silkBagContent = silkBagContent;
        this.straight = silkBagContent[0];
        this.corner = silkBagContent[1];
        this.tShaped = silkBagContent[2];
        this.fire = silkBagContent[3];
        this.ice = silkBagContent[4];
        this.backtrack = silkBagContent[5];
        this.doublemove = silkBagContent[6];
    }
    //throws exception
    //create a new exception class that extends the default exception
    // throws NumberNotMatchingTile expection
    public Tile getTile() {
        int randomNum = rand.nextInt(silkBagContent.length);

        if (silkBagContent[randomNum] <= 0) {
           return getTile();
        } else {
            silkBagContent[randomNum] = silkBagContent[randomNum] - 1;
            switch (randomNum) {
                case Tile.STRAIGHT_TYPE:
                    Tile StraightTile = new StraightTile(randomNum, null);
                    return StraightTile;
                case 1:
                    Tile CornerTile = new CornerTile(randomNum, null);
                    return CornerTile;
                case 2:
                    Tile TShapedTile = new TShapedTile(randomNum, null);
                    return TShapedTile;
                case 3:
                    Tile FireTile = new FireTile(randomNum, null);
                    return FireTile;
                case 4:
                    Tile IceTile = new IceTile(randomNum, null);
                    return IceTile;
                case 5:
                    Tile BackTrackTile = new BacktrackTile(randomNum, null);
                    return BackTrackTile;
                case 6:
                    Tile DoubleMoveTile = new DoubleMoveTile(randomNum, null);
                    return DoubleMoveTile;
            }
        }

    }

    public void insertTileToBag(Tile tile) {
        int tileType = tile.getType();

        this.silkBagContent[tileType]++;

        
    }

}
