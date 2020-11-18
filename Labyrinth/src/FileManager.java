import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;

public class FileManager {

    /**
     *  Creates a Level object for a pre-existing game.
     * @param in passes through the Scanner for the file.
     * @return a Level object
     */

    private static Level loadSaveLevel(Scanner in) {

        String stringProfileName = in.next();
        String nameOfBoard = in.next();
        int gameTurn = in.nextInt();
        String stringSizeOfBoard = in.next();
        String stringProfileCord = in.next();
        String stringProfileCordHistory = in.next();
        String stringSilkBagContent = in.next();
        String stringHeldPlayerTiles = in.next();
        String stringBackTrackCheck = in.next();

        //  Converts strings to more useful data types.
        String[] profileName = stringToStringArray(stringProfileName);
        int[] sizeOfBoard = stringToIntArray(stringSizeOfBoard);
        int[] profileCord = stringToIntArray(stringProfileCord);
        int[] profileCordHistory = stringToIntArray(stringProfileCordHistory);
        int[] silkBagContent = stringToIntArray(stringSilkBagContent);
        Boolean backTrackCheck = Boolean.parseBoolean(stringBackTrackCheck);

        ArrayList<Profile> profiles;
        ArrayList<Profile> usedProfile = new ArrayList<>();
        int[] profileCordX = new int[profileName.length];
        int[] profileCordY = new int[profileName.length];
        int[] profileCordHistoryArray = new int[profileName.length * 3];
        Board tempBoard = new Board(nameOfBoard, sizeOfBoard);
        ArrayList<Tile> t = new ArrayList<>();
        String[] heldPlayerTiles = stringHeldPlayerTiles.split("[;]");
        String[] heldPlayerTilesPlayer = new String[0];
        int counter;


        //  Populates Board with Tiles
        for (int i = 0; i < sizeOfBoard[0]*sizeOfBoard[1]; i++) {
            String stringTile = in.next();
            String[] sta = stringToStringArray(stringTile);

            Tile tempTile = createTempTile(sta[2], Integer.getInteger(sta[3]), sta[4], Boolean.getBoolean(sta[5]));
            tempBoard.insertTile(stringToInt(sta[0]),stringToInt(sta[1]), tempTile);
        }

        //  Reads in profiles
        profiles = readProfileDataFile("Profiles.txt");
        for (int i = 0; i < profileName.length; i++) {
            if (Arrays.asList(profileName).contains(profiles.get(i).getProfileName()) == true) {
                usedProfile.add(profiles.get(i));
            }
        }

        //  Splits ProfileCord X elements from Y elements
        counter = 0;
        for (int i = 0; i < (profileCord.length)/2; i = i + 2, counter++) {
            profileCordX[counter] = profileCord[i];
        }

        //  Splits ProfileCord Y element from X elements.
        counter = 0;
        for (int j = 1; j < (profileCord.length)/2; j = j + 2, counter++){
            profileCordY[j] = profileCord[j];
        }


        //  Creates Player Objects
        counter = 0;

        Player[] players = new Player[profileName.length];
        for (int i = 0; i < profileName.length; i++, counter = counter + 6) {
            for (int j = 0; j < 6; j++) {
                profileCordHistoryArray[j] = profileCordHistory[j + counter];
            }
            
            for (int j = 0; j < heldPlayerTiles.length; j++) {
                heldPlayerTilesPlayer = heldPlayerTiles[j].split(",");
            }

            for (int j = 0; j < heldPlayerTilesPlayer.length; j = j+2) {
                t.add(createHeldTiles(heldPlayerTilesPlayer[j], Integer.getInteger(heldPlayerTilesPlayer[j+1])));
            }

            Player tempPlayer = new Player(usedProfile.get(i), profileCordX[i], profileCordY[i], profileCordHistory,
                    t, backTrackCheck);
            players[i] = (tempPlayer);
        }

        //  silkBag(int[] silkBagContent)
        // ith element = (int Straight,int Corner,int TShaped, int Fire,int Ice,int Backtrack,int Doublemove,int Goal)
        // respectively
        SilkBag silkBag = new SilkBag(silkBagContent);

        return new Level(tempBoard, gameTurn, silkBag, players);
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

            Tile fixedTile = createTempTile(sta[2], Integer.getInteger(sta[3]), sta[4], true);
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

    public static Tile createTempTile(String typeOfTile, int orientation, String state, Boolean isFixed) {
        Tile tempTile = null;

        switch (typeOfTile) {
            case "Straight" :
                tempTile = new StraightTile(orientation, state, isFixed);
                break;
            case "TShaped"  :
                tempTile = new TShapedTile(orientation, state, isFixed);
                break;
            case "Corner"   :
                tempTile = new CornerTile(orientation, state, isFixed);
                break;
            case "Goal"     :
                tempTile = new GoalTile(orientation, state, isFixed);
                break;
            default:
                System.out.println("An error has occurred");
        }
        return tempTile;
    }

    public static Tile createHeldTiles(String typeOfTile, int orientation) {
        Tile tempTile = null;

        switch (typeOfTile) {
            case "Straight" :
                tempTile = new StraightTile(orientation, "normal", false);
                break;
            case "TShaped"  :
                tempTile = new TShapedTile(orientation, "normal", false);
                break;
            case "Corner"   :
                tempTile = new CornerTile(orientation, "normal", false);
                break;
            case "Goal"     :
                tempTile = new GoalTile(orientation, "normal", false);
                break;
            case "Fire"     :
                tempTile = new FireTile();
                break;
            case "Ice"      :
                tempTile = new IceTile();
                break;
            case "DoubleMove"   :
                tempTile = new DoubleMoveTile();
                break;
            case "BackTrack"    :
                tempTile = new BackTrackTile();
                break;

            default:
                System.out.println("An error has occurred");
        }
        return tempTile;
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
