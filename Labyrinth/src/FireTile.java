import java.awt.*;

public class FireTile extends ActionTile {
    public FireTile(int size, Image image) {
        super(size, image);
    }
    public void setFire (int x, int y){
        Board.setTilesOnFire(x, y);

    }
    public void draw (){

    }
}
