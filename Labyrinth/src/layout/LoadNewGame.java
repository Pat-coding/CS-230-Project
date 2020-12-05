package layout;

import backend.Level;
import backend.Profile;

import java.util.ArrayList;

/**
 * This class lets the user choose which board they want to load for their new game with what players.
 * @author
 * @version 1.0
 */

public class LoadNewGame {

    private ArrayList<Profile> profiles;
    private ArrayList<Level> newLevel;

    public LoadNewGame(ArrayList<Profile> profiles, ArrayList<Level> newLevel){
        this.profiles = profiles;
        this.newLevel = newLevel;
    }


}
