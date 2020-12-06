package Tiles;

import Tiles.ActionTile;

import java.awt.*;

/**
 * Used to instantiate a Ice tile.
 * @author Diana Suankulova
 * @version 1.0.0
 */
public class IceTile extends ActionTile {

    private String typeOfTile = "Ice";
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