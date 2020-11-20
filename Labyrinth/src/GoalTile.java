import java.awt.*;

public class GoalTile extends FloorTile{
    private boolean accessFromTop, accessFromBottom, accessFromLeft, accessFromRight, fixed;
    private int orientation;
    private int goalCoordinates;
    public GoalTile(int size, Image image) {
        super(size, image);
    }
}