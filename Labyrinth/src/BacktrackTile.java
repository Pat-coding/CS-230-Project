import java.awt.*;

public class BacktrackTile extends ActionTile {
    public BacktrackTile(int size, Image image) {
        super(size, image);
    }
    public void backTrack (ArrayList<Integer> tilesVisited, int x, int y){
        Board.backTrackPlayer(tilesVisited, x, y);

    }
    public void draw (){

    }
}
