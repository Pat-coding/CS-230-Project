
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
    int goal;
    Random rand;
    private int [] silkBagContent;

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

    public int getTile() {

        int randomNum = rand.nextInt(silkBagContent.length);

        if (silkBagContent[randomNum] <= 0){
            getTile();
        } else {
            silkBagContent[randomNum] = silkBagContent[randomNum] - 1;
            switch (randomNum){
                case 0:
                    Tile StraightTile = new StraightTile(randomNum, null);
                case 1:
                    Tile CornerTile = new CornerTile(randomNum, null);
                case 2:
                    Tile TShapedTile = new TShapedTile(randomNum, null);
                case 3:
                    Tile FireTile = new FireTile(randomNum, null);
                case 4:
                    Tile IceTile = new IceTile(randomNum, null);
                case 5:
                    Tile BackTrackTile = new BackTrackTile(randomNum, null);
                case 6:
                    Tile DoubleMoveTile = new DoubleMoveTile(randomNum, null);
            }
        }

    }


    public static void insertTileToBag(Tile[] tile) {
        this.fire = tile[0];
    }

>>>>>>> main
}
