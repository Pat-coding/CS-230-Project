import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

public class FileManager {

    /**
     *  Creates a Level object for a pre-existing game.
     * @param in passes through the Scanner for the file.
     * @return a Level object
     */

    private static Level loadSaveLevel(Scanner in) {

        String stringProfileID = in.next();
        String nameOfBoard = in.next();
        int gameTurn = in.nextInt();
        String stringSizeOfBoard = in.next();
        String stringProfileCord = in.next();
        String stringProfileCordHistory = in.next();
        String stringSilkBagContent = in.next();
        String stringHeldPlayerTiles = in.next();
        String stringBackTrackCheck = in.next();

        //  Converts strings to more useful data types.
        int[] profileID = stringToIntArray(stringProfileID);
        int[] sizeOfBoard = stringToIntArray(stringSizeOfBoard);
        int[] profileCord = stringToIntArray(stringProfileCord);
        int[] profileCordHistory = stringToIntArray(stringProfileCordHistory);
        String[] silkBagContent = stringToStringArray(stringSilkBagContent);
        String[] heldPlayerTiles = stringToStringArray(stringHeldPlayerTiles);
        Boolean backTrackCheck = Boolean.parseBoolean(stringBackTrackCheck);

        //  Creates Board Object
        Board tempBoard = new Board(nameOfBoard, sizeOfBoard);
        //  Populates Board with Tiles
        for (int i = 0; i < sizeOfBoard[0]*sizeOfBoard[1]; i++) {
            String stringTile = in.next();
            String[] sta = stringToStringArray(stringTile);

            Tile tempTile = new Tile(sta[2],sta[3],sta[4]);
            tempBoard.insertTile(stringToInt(sta[0]),stringToInt(sta[1]), tempTile);
        }

        //  Creates Player Objects
        Player[] players = new Player[profileID.length];
        for (int i = 0; i < profileID.length; i++) {


            Player tempPlayer = new Player(profileID[i], profileCord[i], profileCordHistory[i],
                    heldPlayerTiles[i], backTrackCheck);
            players[i] = (tempPlayer);
        }
        return new Level(tempBoard, gameTurn, silkBagContent, players);
    }

    /**
     *  Creates a Level object for a new game.
     * @param in passes through the Scanner for the file.
     * @return a Level object
     */

    private static Level loadNewLevel(Scanner in) {

        String nameOfBoard = in.next();
        String stringSizeOfBoard = in.next();
        String stringSpawnPoints = in.next();
        String stringSilkBagContent = in.next();
        int numOfFixedTiles = in.nextInt();

        int[] sizeOfBoard = stringToIntArray(stringSizeOfBoard);
        int[] spawnPoints = stringToIntArray(stringSpawnPoints);
        String[] silkBagContent = stringToStringArray(stringSilkBagContent);

        //  details of fixed tiles
        Board tempBoard = new Board(nameOfBoard, sizeOfBoard);
        for (int i = 0; i < numOfFixedTiles - 1; i++) {
            String stringTile = in.next();
            String[] sta = stringToStringArray(stringTile);
            // change parama of insert tiles to int
            Tile fixedTile = new Tile(sta[2],sta[3], true);
            tempBoard.insertTile(stringToInt(sta[0]),stringToInt(sta[1]), fixedTile);
        }

        return new Level(tempBoard, 0, silkBagContent, spawnPoints);
    }

    /**
     *  Creates a Profile object
     * @param in passes through the Scanner for the file.
     * @return a Profile object
     */

    private static Profile loadProfile(Scanner in) {
        String profileName = in.next();
        String stringProfileWinCount = in.next();
        String stringProfileLossCount = in.next();

        int profileWinCount = stringToInt(stringProfileWinCount);
        int profileLossCount = stringToInt(stringProfileLossCount);

        return new Profile(profileName, profileWinCount, profileLossCount);
    }

    /**
     *
     * @param level
     */

    public static void createNewSaveFile(Level level) {

    }

    /**
     *
     * @param name
     */
    public static void createNewProfile (String name) {
        try {
            FileWriter profileWriter = new FileWriter("Profile.txt");
            profileWriter.write(name);

        } catch (IOException e) {
            System.out.println("An error has occurred");
            e.printStackTrace();
        }
    }

    /**
     *  Reads the data file used by the program, and returns a constructed arraylist.
     *
     * @param in the scanner of the file
     * @return the arraylist represented by the data file
     */

    public static ArrayList<Profile> readDataFileProfile(Scanner in) {
        ArrayList<Profile> returnableArray = new ArrayList<Profile>();
        while (in.hasNext()) {
            Profile profile = loadProfile(in);
            returnableArray.add(profile);
        }
        return returnableArray;
    }

    /**
     *  Method to read and turn an arraylist of profiles from this file. The
     *  program should handle the file not found exception here and shut down
     *  the program elegantly
     *
     * @param filename the name of the file
     * @return the ArrayList of Profiles from the file.
     */

    private static ArrayList<Profile> readProfileDataFile(String filename) {
        File inputFile = new File(filename);
        Scanner in = null;
        try {
            in = new Scanner (inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open" + filename);
            System.exit(0);
        }
        return FileManager.readDataFileProfile(in);
    }

    /**
     *  Reads the data file used by the program, and returns a constructed arraylist.
     *
     * @param in the scanner of the file
     * @return the arraylist represented by the data file
     */

    public static ArrayList<Level> readDataFileLevel(Scanner in, String loadType) {
        ArrayList<Level> returnableArray = new ArrayList<Level>();

        while (in.hasNext()) {
            switch (loadType) {
                case "New Level":
                    Level newLevel = loadNewLevel(in);
                    returnableArray.add(newLevel);
                    break;

                case "Saved Level":
                    Level saveLevel = loadSaveLevel(in);
                    returnableArray.add(saveLevel);
                    break;

                default:
                    System.out.println("Error : Cannot identify File type.");
            }
        }
        return returnableArray;
    }

    /**
     *  Method to read and turn an arraylist of level from this file. The
     *  program should handle the file not found exception here and shut down
     *  the program elegantly
     *
     * @param filename the name of the file
     * @return the ArrayList of Profiles from the file.
     */

    private static ArrayList<Level> readLevelDataFile(String filename, String type) {
        File inputFile = new File(filename);
        Scanner in = null;
        try {
            in = new Scanner (inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open" + filename);
            System.exit(0);
        }
        return FileManager.readDataFileLevel(in, type);
    }

    /**
     *  Method to convert a string into a string array, with a particular delimiter
     * @param a the string
     * @return an array with the contents of the string
     */

    private static String[] stringToStringArray(String a) {
        return a.split("[,]");
    }

    /**
     *  Method takes a String, converts it into an Integer, and handles
     *  the NumberFormatException here and shut down the program elegantly
     * @param string the string being converted
     * @return int value
     */

    private static int stringToInt(String string) {
        int returnVal = 0;
        try {
            returnVal = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            System.out.print(e);
        }
        return returnVal;
    }

    /**
     *  Method takes a String, converts it into a Integer array, and handles
     *  the NumberFormatException here and shut down the program elegantly
     * @param a the string
     * @return an array of type int with the contents of the string
     */

    private static int[] stringToIntArray(String a) {
        String[] item = a.split("[,]");

        int [] returnVal = new int[item.length];

        for (int i = 0; i < item.length; i++) {
            try {
                returnVal[i] = Integer.parseInt(item[i]);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
        return returnVal;
    }

}
