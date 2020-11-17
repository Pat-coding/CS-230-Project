import java.awt.*;

public class CornerTile extends FloorTile{
    private boolean accessFromTop, accessFromBottom, accessFromLeft, accessFromRight, fixed;
    private int orientation;
    public CornerTile(int size, Image image) {
        super(size, image);
    }
    public void draw (){

    }
}
