// How to generate children of Tile, Tile tile = new <TileSubType>(args,  kwargs)

import java.awt.*;
public class Tile {

    public boolean fixed;
    public boolean frozen;
    public boolean onFire;
    private int size;
    private Image image;

    protected Tile (int size, Image image){
        this.image = image;
        this.size = size;
    }

    public void setSize (int size){
        this.size = size;
    }
    public void setImage (Image image){

    }

    public void draw (Tile tile){

    }
}
