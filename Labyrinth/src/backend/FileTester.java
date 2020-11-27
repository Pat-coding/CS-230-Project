package backend;

import java.util.ArrayList;

public class FileTester {

    public static ArrayList<Profile> profiles;
    public static ArrayList<Level> savedLevels;
    public static ArrayList<Level> newLevels;

    public static void main(String[] arg) {

        try {
            savedLevels = FileManager.readLevelDataFile("SavedLevel.txt", "Saved Level");

        } catch (Error e) {
            System.out.println("FML");
        }
    }
}
