package Tiles;

/**
 * Used to instantiate a TShaped tile.
 * @author Diana Suankulova
 * @version 1.0.0
 */
public class TShapedTile extends FloorTile {

    private boolean accessFromTop = true;
    private boolean accessFromBottom = true;
    private boolean accessFromLeft = true;
    private boolean accessFromRight = true;
    private String typeOfTile = "TShaped";
    private String image = "PATH";
    private int orientation;
    private boolean isFixed;
    private String state;

    /**
     * A constructor which is intended to be used to create a TShaped Tile.
     *
     * @param orientation of tile
     * @param state of tile
     * @param isFixed true/false
     */

    public TShapedTile(int orientation, String state, Boolean isFixed) {
        super(orientation, state, isFixed);
        this.isFixed = isFixed;
        this.setOrientation(orientation);
        if (orientation == 0) {
            this.accessFromTop = false;
        }
        if (orientation == 90 ) {
            this.accessFromRight = false;
        }
        if (orientation == 180 ) {
            this.accessFromBottom = false;
        }
        if (orientation == 270 ){
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

    /**
     * Sets the orientation of a Tile.
     * @param orientation desired orientation
     */
    @Override
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
