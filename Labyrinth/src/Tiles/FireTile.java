package Tiles;

/**
 * Used to instantiate a Fire tile.
 * @author Diana Suankulova
 * @version 1.0.0
 */
public class FireTile extends ActionTile {
    private String typeOfTile = "Fire";


    /**
     * @return the type of Tile as a String.
     */

    @Override
    public String getType() {
        return typeOfTile;
    }

    /**
     * @return the orientation of the tile.
     */

    @Override
    public int getOrientation() {
        return 0;
    }

}
