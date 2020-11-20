package Tiles;

public class TShapedTile extends FloorTile {

    private boolean accessFromTop = true;
    private boolean accessFromBottom = true;
    private boolean accessFromLeft = true;
    private boolean accessFromRight = true;
    private String typeOfTile = "TShapedTile";
    private String image = "PATH";
    private int orientation;
    private boolean isFixed;
    private String state;


    public TShapedTile(int orientation, String state, Boolean isFixed) {
        super(orientation, state, isFixed);

        if (orientation == 0) {
            this.accessFromTop = false;
        } else if (orientation == 90 ) {
            this.accessFromRight = false;
        } else if (orientation == 180 ) {
            this.accessFromBottom = false;
        } else {
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
     * @param tile
     * @return
     */
    @Override
    public void draw(Tile tile) {

    }


}
