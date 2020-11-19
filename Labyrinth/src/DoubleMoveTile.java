import java.awt.*;

public class DoubleMoveTile extends ActionTile {
    public DoubleMoveTile (int size, Image image) {
        super(size, image);
    }
    public void DoubleMove (ArrayList<Integer> tilesVisited, int x, int y){
        Board.backTrackPlayer(tilesVisited, x, y);

    }
    public void draw (){

    }
}