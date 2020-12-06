package Tiles;


/**
 * The parent of the Tiles Class
 * @author Diana Suankulova
 * @version 1.0.0
 */
public abstract class Tile {

    /**
     * Default constructor
     */
    protected Tile() {
    }

    /**
     * Used for getting information regarding a tile.
     * @return the type of tile
     */
    public abstract String getType();

    /**
     * Used for getting in regarding orientation.
     * @return orientation of the object.
     */
    public abstract int getOrientation();


}
