package Tiles;

/**
 * The parent of the Floor Tile.
 * @author Diana Suankulova
 * @version 1.0.0
 */
public abstract class FloorTile extends Tile {

    protected boolean accessFromTop;
    protected boolean accessFromBottom;
    protected boolean accessFromLeft;
    protected boolean accessFromRight;
    protected String typeOfTile;
    private int orientation;
    protected boolean isFixed;
    protected String state;

    /**
     * A constructor which is intended to be used to create a child of
     * FloorTile
     * @param orientation of tile
     * @param state of tile
     * @param isFixed true/false
     */

    protected FloorTile(int orientation, String state, Boolean isFixed) {
        this.setOrientation(orientation);
        this.state = state;
        this.isFixed = isFixed;

    }

    /**
     * @return the type of Tile as a String.
     */

    public abstract String getType();

    /**
     * @return if player can access tile from the top of the tile.
     */

    public abstract boolean isAccessFromTop();

    /**
     * @return if player can access tile from the bottom of the tile.
     */

    public abstract boolean isAccessFromBottom();

    /**
     * @return if player can access tile from the left of the tile.
     */

    public abstract boolean isAccessFromLeft();

    /**
     * @return if player can access tile from the right of the tile.
     */

    public abstract boolean isAccessFromRight();

    /**
     * @return the orientation of the tile.
     */

    public abstract int getOrientation();

    /**
     * @return the the state of fixed.
     */
    public abstract boolean isFixed();

    /**
     * @return gets the state of the tile.
     */

    public abstract String getState();

    /**
     * Sets the state of a String.
     * @param state desired state
     */

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Sets the orientation of a Tile.
     * @param orientation desired orientation
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }




}