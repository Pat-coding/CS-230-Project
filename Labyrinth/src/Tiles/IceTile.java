package Tiles;

import Tiles.ActionTile;

import java.awt.*;

public class IceTile extends ActionTile {

    private String typeOfTile = "Ice";
    private String image = "PATH";

    @Override
    public String getType() {
        return typeOfTile;
    }


}