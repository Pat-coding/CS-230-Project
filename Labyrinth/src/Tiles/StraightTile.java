package Tiles;

import java.awt.*;

public class StraightTile extends FloorTile {
    private boolean accessFromTop = true;
    private boolean accessFromBottom = true;
    private boolean accessFromLeft = true;
    private boolean accessFromRight = true;
    private int orientation;
    private boolean isFixed;
    private String state;
    private String typeOfTile = "Straight";
    private String image = "PATH";

    public StraightTile(int orientation, String state, Boolean isFixed) {
        super(orientation, state, isFixed);
        this.orientation = orientation;
        this.isFixed = isFixed;

        if (orientation == 0 || orientation == 180) {
            this.accessFromTop = false;
            this.accessFromBottom = false;
        }
        if (orientation == 90 || orientation == 270) {
            this.accessFromRight = false;
            this.accessFromLeft = false;
        }
    }

    /**
     * @return the type of Tile as a String.
     */
    @Override
    public String getType() {
        return typeOfTile;
    }

    /**
     * @return if player can access tile from the top of the tile.
     */
    @Override
    public boolean isAccessFromTop() {
        return accessFromTop;
    }

    /**
     * @return if player can access tile from the bottom of the tile.
     */
    @Override
    public boolean isAccessFromBottom() {
        return accessFromBottom;
    }

    /**
     * @return if player can access tile from the left of the tile.
     */
    @Override
    public boolean isAccessFromLeft() {
        return accessFromLeft;
    }

    /**
     * @return if player can access tile from the right of the tile.
     */
    @Override
    public boolean isAccessFromRight() {
        return accessFromRight;
    }

    /**
     * @return the orienation of the tile.
     */
    @Override
    public int getOrientation() {
        return orientation;
    }

    /**
     * @return the the state of fixed.
     */
    @Override
    public boolean isFixed() {
        return isFixed;
    }

    /**
     * @return gets the state of the tile.
     */
    @Override
    public String getState() {
        return state;
    }



}