package backend;

import Tiles.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        String roundNumber = in.next();
        String stringSizeOfBoard = in.next();
        String stringProfileCord = in.next();
        String stringProfileCordHistory = in.next();
        String stringSilkBagContent = in.next();
        String stringPlayerInventory = in.next();
        String stringBackTrackCheck = in.next();
        String stringIsPlayerTurn = in.next();

        //  Converts strings to more useful data types.
        String[] profileName = stringToStringArray(stringProfileName);
        String[] isPlayerTurn = stringToStringArray(stringIsPlayerTurn);
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
        Board tempBoard = new Board(nameOfBoard, sizeOfBoard, profileName);


        String[] playerInventory = stringPlayerInventory.split("[;]");

        ArrayList<Tile> p0 = new ArrayList<>();
        ArrayList<Tile> p1 = new ArrayList<>();
        ArrayList<Tile> p2 = new ArrayList<>();
        ArrayList<Tile> p3 = new ArrayList<>();

        List<Tile>[] arrayOfList = new List[4];
        arrayOfList[0] = p0;
        arrayOfList[1] = p1;
        arrayOfList[2] = p2;
        arrayOfList[3] = p3;

        int counter;
        //  Populates Board with Tiles
        for (int i = 0; i < sizeOfBoard[0]*sizeOfBoard[1]; i++) {
            String stringTile = in.next();
            String[] sta = stringToStringArray(stringTile);

            FloorTile tempTile = createTempTile(sta[2], Integer.parseInt(sta[3]), sta[4], Boolean.parseBoolean(sta[5]));
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
        ArrayList<Tile> playerInventoryArrayListTemp = new ArrayList<>();
        Player[] players = new Player[profileName.length];
        for (int i = 0; i < profileName.length; i++, counter = counter + 6) {
            String[] playerInventoryTemp = playerInventory[i].split(",");


            //  Takes the first 6 numbers in the array
            for (int j = 0; j < 6; j++) {
                profileCordHistoryArray[j] = profileCordHistory[j + counter];
            }

            for (int j = 0; j < playerInventoryTemp.length - 1; j = j+2) {
                if (playerInventoryTemp[j] == "NA"){
                    break;
                }
                arrayOfList[i].add(createPlayerInventoryTiles(playerInventoryTemp[j], Integer.parseInt(playerInventoryTemp[j+1])));
            }

            Player tempPlayer = new Player(usedProfile.get(i), profileCordX[i], profileCordY[i], profileCordHistory,
                    (ArrayList<Tile>) arrayOfList[i], backTrackCheck, Boolean.parseBoolean(isPlayerTurn[i]));
            players[i] = (tempPlayer);
            playerInventoryArrayListTemp.clear();

        }

        SilkBag silkBag = new SilkBag(silkBagContent);

        return new Level(tempBoard, Integer.parseInt(roundNumber), silkBag, players);
    }




    /**
     *  Creates a Level object for a new game.
     * @param in passes through the Scanner for the file.
     * @return a Level object
     */

    private static Level loadNewLevel(Scanner in) {
        try {
            String nameOfBoard = in.next();
            String stringSizeOfBoard = in.next();
            String stringSpawnPoints = in.next();
            String stringSilkBagContent = in.next();
            int numOfFixedTiles = in.nextInt();


            int[] sizeOfBoard = stringToIntArray(stringSizeOfBoard);
            int[] spawnPoints = stringToIntArray(stringSpawnPoints);
            int[] silkBagContent = stringToIntArray(stringSilkBagContent);

            //  details of fixed tiles
            Board tempBoard = new Board(nameOfBoard, sizeOfBoard);
            for (int i = 0; i < numOfFixedTiles; i++) {

                String stringTile = in.next();
                String[] sta = stringToStringArray(stringTile);

                FloorTile fixedTile = createTempTile(sta[2], stringToInt(sta[3]), sta[4], true);

                tempBoard.insertTile(stringToInt(sta[0]), stringToInt(sta[1]), fixedTile);
            }

            SilkBag silkBag = new SilkBag(silkBagContent);

            return new Level(tempBoard, 0, silkBag, spawnPoints);
        } catch (Error e) {
            System.out.println(e);
        }


        return null;
    }

    /**
     *  Creates a Profile object
     * @param in passes through the Scanner for the file.
     * @return a Profile object
     */
    // throws ProfileNotCompleteException
    private static Profile loadProfile(Scanner in){

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
        Board board = level.getBoardData();
        int gameTurn = level.gameTurnData;
        SilkBag silkBag = level.getSilkBag();
        Player[] player = level.getPlayerData();

        try (FileWriter levelWriter = new FileWriter("SaveFile.txt")) {
            for (int i = 0; i < player.length - 1; i++) {
                if (i == player.length - 1) {
                    levelWriter.write(player[i].getProfile().getProfileName() + "\n");
                    break;
                }
                levelWriter.write(player[i].getProfile().getProfileName() + ",");
            }

            //  Name
            levelWriter.write(board.getNameOfBoard() + "\n");
            //  Game Turn
            levelWriter.write(gameTurn + "\n");
            //  Size of Board
            levelWriter.write(board.getRowSize() + "," + board.getColumnSize() + "\n");
            //  Profile Coordinate

            for (int i = 0; i < player.length - 1; i++) {
                levelWriter.write(player[i].getPlayerCordX() + "," + player[0].getPlayerCordY() + "\n");
            }

            //  Profile Coordinate History
            for (int i = 0; i < player.length - 1; i++) {
                levelWriter.write(Arrays.toString(player[i].getProfileCordHistory()) + "\n");
            }

            //  Contents of the Silk Bag
            levelWriter.write(Arrays.toString(silkBag.getSilkBagContent()) + "\n");

            //  Player inventory
            levelWriter.write(player[0].getPlayerInventory() + ";" + player[1].getPlayerInventory() + ";" +
                    player[2].getPlayerInventory() + ";" + player[3].getPlayerInventory());
            //  Backtrack
            levelWriter.write(player[0].getBackTrackCheck() + "," + player[1].getBackTrackCheck() + ","
                    + player[2].getBackTrackCheck() + "," + player[3].getBackTrackCheck());
            //  ENTIRE BOARD
            for (int i = 0; i < board.getRowSize()*board.getColumnSize(); i++) {
                for (int j = 0; j < board.getRowSize()*board.getColumnSize(); i++) {
                    levelWriter.write(board.getTileFromBoard(i,j) + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("An error has occurred");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param name
     */
    public static void createNewProfile (String name) {
        try (FileWriter profileWriter = new FileWriter("Profiles.txt")){

            profileWriter.write(name + "\n");
            profileWriter.write("0\n");
            profileWriter.write("0");

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

    private static ArrayList<Profile> readDataFileProfile(Scanner in) {
        ArrayList<Profile> returnableArray = new ArrayList<Profile>();

        while (in.hasNext()) {
            try {
                Profile profile = loadProfile(in);
                returnableArray.add(profile);
            } catch (Error e) {
                System.out.print("An unknown error has occurred.");
            }
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

    public static ArrayList<Profile> readProfileDataFile(String filename) {
        File inputFile = new File(filename);

        Scanner in = null;
        try {
            in = new Scanner (inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
            System.out.print(e);
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

    private static ArrayList<Level> readDataFileLevel(Scanner in, String loadType) {
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
        in.close();
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

    public static ArrayList<Level> readLevelDataFile(String filename, String type) {
        File inputFile = new File(filename);
        Scanner in = null;
        try {
            in = new Scanner (inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
            System.exit(0);
        }
        return FileManager.readDataFileLevel(in, type);
    }

    private static FloorTile createTempTile(String typeOfTile, int orientation, String state, Boolean isFixed) {

        FloorTile tempTile = null;
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

    public static Tile createPlayerInventoryTiles(String typeOfTile, int orientation) {
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
                tempTile = new BacktrackTile();
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
