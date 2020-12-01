package Tiles;

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
