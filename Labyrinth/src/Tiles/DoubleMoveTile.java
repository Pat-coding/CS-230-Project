package Tiles;

import Tiles.ActionTile;

import java.awt.*;

public class DoubleMoveTile extends ActionTile {

    private String typeOfTile ="DoubleMoveTile";
    private String image = "PATH";
    public DoubleMoveTile() {

    }

    @Override
    public String getType() {
        return typeOfTile;
    }

    @Override
    public void draw(Tile tile) {

    }
}