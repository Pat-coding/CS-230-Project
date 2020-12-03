package Tiles;

import Tiles.ActionTile;

import java.awt.*;

public class DoubleMoveTile extends ActionTile {

    private String typeOfTile ="DoubleMove";
    private String image = "PATH";
    public DoubleMoveTile() {

    }

    @Override
    public String getType() {
        return typeOfTile;
    }

}