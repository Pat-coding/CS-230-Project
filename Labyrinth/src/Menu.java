
import java.util.ArrayList;

public class Menu {

    ArrayList<Profile> profiles = FileManager.readProfileDataFile("Profile.txt");
    ArrayList<Level> savedLevels = FileManager.readLevelDataFile("SavedLevels.txt", "Saved Level");
    ArrayList<Level> newLevels = FileManager.readLevelDataFile("NewLevels.txt", "New Level");

    public static void main(String[] args) {
        String hello = "Hello";
        System.out.println(hello);
    }


}
