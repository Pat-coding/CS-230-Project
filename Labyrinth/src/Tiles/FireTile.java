package Tiles;

public class FireTile extends ActionTile {
    private String typeOfTile = "Fire";
    private String image = "PATH";


    @Override
    public String getType() {
        return typeOfTile;
    }

    @Override
    public int getOrientation() {
        return 0;
    }

}
