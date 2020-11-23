package backend;

import java.util.ArrayList;

public class Menu {

    public static ArrayList<Profile> profiles = FileManager.readProfileDataFile("Profile.txt");
    public ArrayList<Level> savedLevels = FileManager.readLevelDataFile("SavedLevels.txt", "Saved Level");
    public ArrayList<Level> newLevels = FileManager.readLevelDataFile("NewLevels.txt", "New Level");

    public Menu() {

    }

    public static void main(String[] args) {
        String hello = "Hello";
        System.out.println(hello);
    }


    public void createNewProfile(String name){
        profiles.add(new Profile(name, 0, 0));
        FileManager.createNewProfile(name);
    }

}
