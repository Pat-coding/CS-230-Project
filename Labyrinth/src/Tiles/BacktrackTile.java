package Tiles;

public class BacktrackTile extends ActionTile {

    private String typeOfTile ="Backtrack";
    private String image = "PATH";

    public BacktrackTile() {

    }

    @Override
    public String getType() {
        return typeOfTile;
    }

    @Override
    public int getOrientation() {
        return 0;
    }


}
