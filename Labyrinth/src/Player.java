/**
 * @author Kane
 * @version 1.0
 */

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Player {

    public boolean hasBeenDoubled;
    private ArrayList<Integer> tilesVisited =  new ArrayList<Integer>();
    private ArrayList<Tile> playerInventory = new ArrayList<Tile>();
    private boolean hasBeenBackTracked;
    private Color playercolor;
    private boolean isPlayerTurn;
    private Profile name;


    public Player(int i, int i1, int i2, String heldPlayerTile, Boolean backTrackCheck) {
    }

    public Player(Integer integer, int spawnPoint, Object o, Object heldPlayerTile, boolean backTrackCheck) {
    }
}
