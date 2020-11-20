package Tiles;

import java.awt.*;

public class FireTile extends ActionTile {
    private String typeOfTile = "FireTile";
    private String image = "PATH";


    @Override
    public String getType() {
        return typeOfTile;
    }

    @Override
    public void draw(Tile tile) {

    }
}
