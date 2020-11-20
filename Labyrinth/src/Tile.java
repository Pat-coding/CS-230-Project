// How to generate children of Tile, Tile tile = new <TileSubType>(args,  kwargs)

import java.awt.*;
public abstract class Tile {

    public static final int STRAIGHT_TYPE = 0;
    public static final int CORNER_TYPE = 0;
    public static final int TSHAPED_TYPE = 0;
    public static final int FIRE_TYPE = 0;
    public static final int ICE_TYPE = 0;
    public static final int BACKTRACK_TYPE = 0;
    public static final int DOUBLEMOVE_TYPE = 0;
    public static final int GOAL_TYPE = 0;
    //add all the types of tiles 

    public boolean fixed;
    public boolean frozen;
    public boolean onFire;
    private int size;
    private Image image;

    protected Tile (Image image){
        this.image = image;
        this.size = size;
    }

    public void setImage (Image image){
        this.image = image;
    }

    public void draw (Tile tile){

    }

    public abstract int getType();
}
