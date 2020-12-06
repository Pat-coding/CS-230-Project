package Tiles;

/**
 * Used for Instantiating a BackTrack Tile.
 * @author Diana Suankulova
 * @version 1.0.0
 */
public class BacktrackTile extends ActionTile {

    private String typeOfTile ="Backtrack";


    /**
     * Used for getting information regarding a tile.
     * @return the type of tile
     */

    @Override
    public String getType() {
        return typeOfTile;
    }

    /**
     * Used for getting in regarding orientation.
     * @return orientation of the object.
     */

    @Override
    public int getOrientation() {
        return 0;
    }


}
