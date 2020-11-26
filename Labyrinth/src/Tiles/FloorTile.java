package Tiles;


public abstract class FloorTile extends Tile {

    protected boolean accessFromTop;
    protected boolean accessFromBottom;
    protected boolean accessFromLeft;
    protected boolean accessFromRight;
    protected String typeOfTile;
    protected String image = "Path";
    protected int orientation;
    protected boolean isFixed;
    protected String state;



    protected FloorTile(int orientation, String state, Boolean isFixed) {
        this.orientation = orientation;
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
     * @return the orienation of the tile.
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

    public void setState(String state) {
        this.state = state;
    }
    /**
     *
     * @return
     */

    public abstract void draw (Tile tile);


}