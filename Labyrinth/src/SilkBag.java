
import java.util.Random;

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

    public SilkBag(int[] silkBagContent) {
        this.straight = silkBagContent[0];
        this.corner = silkBagContent[1];
        this.tShaped = silkBagContent[2];
        this.goal = silkBagContent[3];
        this.fire = silkBagContent[4];
        this.ice = silkBagContent[5];
        this.backtrack = silkBagContent[6];
        this.doublemove = silkBagContent[7];
    }

    public int getTile() {

        int randomNum = rand.nextInt((7));

        switch (randomNum) {
            case 0:
                if ((this.straight = this.straight - 1) < 0){
                    return getTile();
            } else {
                    this.straight = this.straight - 1;
                    Tile fireTile = new FireTile();
                }
        }

    }


    public static void insertTileToBag(Tile[] tile) {
        this.fire = tile[0];
    }

}
