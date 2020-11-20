import java.awt.*;

public class TShapedTile extends FloorTile{
    private boolean accessFromTop, accessFromBottom, accessFromLeft, accessFromRight, fixed;
    private int orientation;
    public TShapedTile(int size, Image image) {
        super(size, image);
    }
    public void draw (){

    }
}
