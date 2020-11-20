import java.awt.*;

public class StraightTile extends FloorTile{
    private boolean accessFromTop, accessFromBottom, accessFromLeft, accessFromRight, fixed;
    private int orientation;
    public StraightTile(int size, Image image) {
        super(size, image);
    }
    public void draw (){

    }
}