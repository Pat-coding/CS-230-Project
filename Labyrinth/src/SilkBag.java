import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SilkBag {
    private static ArrayList<Tile> silkBag;
    private Random randomTile;

    public SilkBag() {
        silkBag = new ArrayList<Tile>();
        randomTile = new Random();
    }

    /**
     * This method returns the silk bag
     * 
     * @return an arraylist of tiles contained in the silk bag
     */
    public List<Tile> getFromSilkBag() {
        return (silkBag);

    }
    // ArrayList<Tile> inventory = inventory.getFromSilkBag(); catch the arraylist

    /**
     * this method retrieves a random Tile from the Silk Bag
     * 
     * @return random tile from the silk bag of the class Tile
     */
    public Tile giveTile() {
        int index = randomTile.nextInt(silkBag.size());
        Tile pickedTile = silkBag.get(index);
        return pickedTile;
    }

    /**
     * This method will insert a discarded tile to the silk bag
     * 
     * @param tile is a discarded tile
     */
    public static void insertTileToBag(Tile tile) {
        silkBag.add(tile);
    }

}
