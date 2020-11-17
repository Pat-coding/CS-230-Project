import java.awt.*;

public class IceTile extends ActionTile {
    public IceTile(int size, Image image) {
        super(size, image);
    }
    public void setIce (int x, int y){
        Board.setTilesFrozen(x, y);

    }
    public void draw (){

    }
}