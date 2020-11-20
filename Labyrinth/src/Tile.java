// How to generate children of Tile, Tile tile = new <TileSubType>(args,  kwargs)

import java.awt.*;
public abstract class Tile {



    private int size;
    private String typeOfType;
    private Image image;

    protected Tile (){
        this.image = image;
        this.size = size;
    }

    public void setImage (Image image){
        this.image = image;
    }


    public abstract String getType();

    public void draw (Tile tile){

    }
}
