package backend;

import java.io.File;
import java.util.ArrayList;

public class FileTester {

    public static ArrayList<Profile> profiles;
    public static ArrayList<Level> savedLevels;
    public static ArrayList<Level> newLevels;

    public static void main(String[] arg) {
        try {
            profiles = FileManager.readProfileDataFile("Profiles.txt");
        } catch (Error e) {
            System.out.println("A fatal error has occurred lolcats");
        }

        try {
            newLevels = FileManager.readLevelDataFile("NewLevel.txt", "New Level");

        } catch (Error e) {
            System.out.print("A fatal Error has occurred with the NewLevel File Reader");
        }finally {
            System.out.println("NewLevels has been successfully run.");
            System.out.println(newLevels);
            System.out.println(newLevels.get(0).getBoardData().getTileFromBoard(5,5));
        }

        try {
            savedLevels = FileManager.readLevelDataFile("SavedLevel.txt", "Saved Level");
            Player[] player = savedLevels.get(0).getPlayerData();
            System.out.println(player[1].getPlayerInventory());
        } catch (Error e) {
            System.out.println("FML");
        }
    }
}
