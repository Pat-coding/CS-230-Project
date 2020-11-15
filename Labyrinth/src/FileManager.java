
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class FileManager {

    public static Level loadSaveLevel(Scanner in) {

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
            tempBoard.insertTile(sta[0],sta[1], tempTile);
            // [tile,tile,tile,tile]
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

    public static Level loadNewLevel(Scanner in) {

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
            tempBoard.insertTile(sta[0],sta[1], fixedTile);
        }

        Player[] players = new Player[4];

        for (int i = 0; i < (players.length - 1); i++) {

            Player tempPlayer = new Player(null, spawnPoints[i], null,
                    null, false);
            players[i] = (tempPlayer);
        }


        return new Level(tempBoard, 0, silkBagContent, players);
    }

    public static Profile loadProfile(Scanner in) {
        String profileName = in.next();
        String profileWinCount = in.next();

        return  new Profile(profileID, profileName, profileWinCount);
    }

    public static ArrayList<Profile> readDataFileProfile(Scanner in) {
        ArrayList<Profile> returnableArray = new ArrayList<Profile>();
        while (in.hasNext()) {
            Profile profile = loadProfile(in);
            returnableArrayProfile.add(profile);
        }
        return returnableArray;
    }

    public static ArrayList<Profile> readProfileDataFile(String filename) {
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

    public static ArrayList<Level> readLevelDataFile(String filename, String type) {
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

    private static String[] stringToStringArray(String a) {
        return a.split("[,]");
    }

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
