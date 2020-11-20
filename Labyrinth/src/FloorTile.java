import java.awt.*;

public class FloorTile extends Tile {
    protected boolean accessFromTop;
    protected boolean accessFromBottom;
    protected boolean accessFromLeft;
    protected boolean accessFromRight;
    protected int orientation;
    protected boolean fixed;


    protected FloorTile(int size, Image image) {
        super(size, image);
    }
    public void draw (){

    }
    protected void setTop(boolean accessFromTop){
        this.accessFromTop = accessFromTop;
    }
    protected boolean getTop(){
        return accessFromTop;
    }
    protected boolean getBottom(){
        return accessFromBottom;
    }
    protected boolean getLeft(){
        return accessFromLeft;
    }
    protected boolean getRight(){
        return accessFromRight;
    }
    protected void setOrientation (int orientation){
        this.orientation = orientation;
    }
    protected int getOrientation (){
        return orientation;
    }
    protected void setBottom(boolean accessFromBottom){
        this.accessFromBottom = accessFromBottom;
    }
    protected void setLeft(boolean accessFromLeft){
        this.accessFromLeft = accessFromLeft;
    }
    protected void setRight(boolean accessFromRight){
        this.accessFromRight = accessFromRight;
    }
    protected void setFixed(boolean fixed){
        this.fixed= fixed;
    }
    protected boolean getFixed(){
        return fixed;
    }
}