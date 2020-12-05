package backend;

import Tiles.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



/**
 *  Class is used to manage all File Reading and Writing to the files.
 * @author Ashley Hayre
 * @version 1.0
 */

public class FileManager {
    private final static int MAXIMUM_NUMBER_OF_PLAYERS = 4;
    private final static int COORDINATE_HISTORY = 3;

    private static int counter;

    /**
     * Creates a Level object for a pre-existing game.
     *
     * @param in passes through the Scanner for the file.
     * @return a Level object
     */
    private static Level loadSaveLevel(Scanner in) {

        //  Reads in lines as Strings
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


        //  Changes the types of some Strings to more useful types.
        String[] profileName = stringToStringArray(stringProfileName);
        int[] sizeOfBoard = stringToIntArray(stringSizeOfBoard);
        int[] profileCord = stringToIntArray(stringProfileCord);
        int[] profileCordHistory = stringToIntArray(stringProfileCordHistory);
        int[] silkBagContent = stringToIntArray(stringSilkBagContent);
        Boolean[] backTrackCheck = stringToBooleanArray(stringBackTrackCheck);
        Boolean[] isPlayerTurn = stringToBooleanArray(stringIsPlayerTurn);

        //  Reads in Profiles
        ArrayList<Profile> profiles;
        ArrayList<Profile> usedProfile = new ArrayList<>();

        //
        int[] profileCordX = new int[profileName.length];
        int[] profileCordY = new int[profileName.length];
        int[] profileCordHistoryArray =
                new int[profileName.length * COORDINATE_HISTORY];

        //  Creates a Board Object
        Board tempBoard = new Board(nameOfBoard, sizeOfBoard, profileName);

        String[] playerInventory = stringPlayerInventory.split("[;]");

        ArrayList<Tile> p0 = new ArrayList<>();
        ArrayList<Tile> p1 = new ArrayList<>();
        ArrayList<Tile> p2 = new ArrayList<>();
        ArrayList<Tile> p3 = new ArrayList<>();

        List<Tile>[] arrayOfList =
                new List[MAXIMUM_NUMBER_OF_PLAYERS];
            arrayOfList[0] = p0;
            arrayOfList[1] = p1;
            arrayOfList[2] = p2;
            arrayOfList[3] = p3;


        //  Populates Board with Tiles
        for (int i = 0; i < sizeOfBoard[0] * sizeOfBoard[1]; i++) {
            //  Continues to read lines
            String stringTile = in.next();
            String[] sta = stringToStringArray(stringTile);
            FloorTile tempTile = createTempTile(sta[2], Integer.parseInt(sta[3]),
                    sta[4], Boolean.parseBoolean(sta[5]));
            tempBoard.insertTile(stringToInt(sta[0]),
                    stringToInt(sta[1]), tempTile);
        }


        //  Reads in profiles
        profiles = readProfileDataFile("Profiles.txt");
        for (Profile profile : profiles) {
            if (Arrays.asList(profileName).contains(profile.getProfileName())) {
                usedProfile.add(profile);
            }
        }

        //  Splits ProfileCord X elements from Y elements
        counter = 0;
        for (int i = 0; i < (profileCord.length); i = i + 2, counter++) {
            profileCordX[counter] = profileCord[i];
        }

        //  Splits ProfileCord Y element from X elements.
        counter = 0;
        for (int j = 1; j < (profileCord.length); j = j + 2, counter++) {
            profileCordY[counter] = profileCord[j];
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

            for (int j = 0; j < playerInventoryTemp.length - 1; j = j + 2) {
                if (!playerInventoryTemp[j].equals("NA")) {
                    arrayOfList[i].add(
                            createPlayerInventoryTiles(playerInventoryTemp[j],
                                    Integer.parseInt(playerInventoryTemp[j + 1])));
                }
            }

            Player tempPlayer = new Player(usedProfile.get(i),
                    profileCordX[i], profileCordY[i], profileCordHistory,
                    (ArrayList<Tile>) arrayOfList[i], backTrackCheck[i],
                    isPlayerTurn[i]);

            players[i] = tempPlayer;
            tempBoard.insertPlayer(profileCordX[i], profileCordY[i], tempPlayer);

            playerInventoryArrayListTemp.clear();
        }

        SilkBag silkBag = new SilkBag(silkBagContent);
        return new Level(tempBoard, Integer.parseInt(roundNumber), silkBag, players);
    }

    /**
     * Creates a Level object for a new game.
     *
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

                FloorTile fixedTile = createTempTile(sta[2],
                        stringToInt(sta[3]), sta[4], true);

                tempBoard.insertTile(stringToInt(sta[0]),
                        stringToInt(sta[1]), fixedTile);
            }

            SilkBag silkBag = new SilkBag(silkBagContent);

            return new Level(tempBoard, 0, silkBag, spawnPoints);
        } catch (Error e) {
            System.out.println(e);
        }


        return null;
    }

    /**
     * Creates a Profile object.
     *
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
     * This method will populate the SavedLevel.txt with changes made to Saved Levels.
     *
     * @param levelArray is an ArrayList containing levels.
     */
    public static void createNewSaveFile(ArrayList<Level> levelArray) {

        //  Clear the file.
        try (PrintWriter dumpFile = new PrintWriter("SavedLevel.txt")) {
            dumpFile.print("");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }


        for (int i = 0; i < levelArray.size(); i++) {
            Board board = levelArray.get(i).getBoardData();
            int gameTurn = levelArray.get(i).getGameTurnData();
            SilkBag silkBag = levelArray.get(i).getSilkBag();
            Player[] player = levelArray.get(i).getPlayerData();

            try (FileWriter levelWriter =
                         new FileWriter("SavedLevel.txt", true)) {

                //  This is used to write the profile names
                for (int j = 0; j < player.length; j++) {
                    if (j < player.length - 1) {
                        levelWriter.write(
                                player[j].getProfile().getProfileName() + ",");
                    } else {
                        levelWriter.write(
                                player[j].getProfile().getProfileName());
                    }
                }

                levelWriter.write("\n" + board.getNameOfBoard() + "\n");
                levelWriter.write(gameTurn + "\n");
                levelWriter.write(
                        board.getRowSize() + ","
                                +  board.getColumnSize() + "\n");

                //  Prints X and Y cords for player
                for (int j = 0; j < player.length; j++) {
                    if (j < player.length - 1) {
                        levelWriter.write(
                                player[j].getPlayerCordX() + ","
                                        + player[j].getPlayerCordY() + ",");
                    } else {
                        levelWriter.write(
                                player[j].getPlayerCordX() + ","
                                        + player[j].getPlayerCordY() + "\n");
                    }
                }

                //  Profile Coordinate History
//                String x = Arrays.toString(player[0].getProfileCordHistory())
//                        .replace("[", "")
//                        .replace("]", "")
//                        .replace(" ", "");
                levelWriter.write(
                        "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"
                                + ",0,0,0,0,0,0,0,0,0,0,0,0,0" + "\n");

                //  Contents of the Silk Bag
                levelWriter.write(Arrays.toString(silkBag.getSilkBagContent())
                        .replace("[", "")
                        .replace("]", "")
                        .replace(" ", "") + "\n");

                //  Player inventory
                for (int j = 0; j < player.length; j++) {
                    if (j == player.length - 1) {
                        if (player[j].getPlayerInventory().size() == 0) {
                            levelWriter.write("NA\n");
                        } else {
                            for (int k = 0; k < player[j].getPlayerInventory().size(); k++) {
                                if (player[j].getPlayerInventory().size() == 0) {
                                    levelWriter.write("NA;");
                                } else {
                                    if (k == player[j].getPlayerInventory().size() - 1) {
                                        levelWriter.write(player[j].getPlayerInventory().get(k).getType() + ",0;");
                                    } else {
                                        levelWriter.write(player[j].getPlayerInventory().get(k).getType() + ",0,");
                                    }
                                }
                            }
                        }
                    } else if (player[j].getPlayerInventory().size() == 0) {
                        levelWriter.write("NA;");
                    }
                    for (int k = 0; k < player[j].getPlayerInventory().size(); k++) {
                        //  if j player is the last player
                        if (j == player.length - 1) {
                            //  will not write ; if j is the last element
                            if (player[j].getPlayerInventory().size() == 0) {
                                levelWriter.write("NA\n");
                            } else {
                                if (k == player[j].getPlayerInventory().size() - 1) {
                                    levelWriter.write(player[j].getPlayerInventory().get(k).getType() + ",0\n");
                                } else {
                                    levelWriter.write(player[j].getPlayerInventory().get(k).getType() + ",0");
                                }
                            }
                        } else {
                            if (player[j].getPlayerInventory().size() == 0) {
                                levelWriter.write("NA;");
                            } else {
                                if (k == player[j].getPlayerInventory().size() - 1) {
                                    levelWriter.write(player[j].getPlayerInventory().get(k).getType() + ",0;");
                                } else {
                                    levelWriter.write(player[j].getPlayerInventory().get(k).getType() + ",0,");
                                }
                            }
                        }
                    }
                }


                //  BackTrack check
                for (int j = 0; j < player.length; j++) {
                    if (j < player.length - 1) {
                        levelWriter.write(player[j].getBackTrackCheck() + ",");
                    } else {
                        levelWriter.write(player[j].getBackTrackCheck() + "\n");
                    }
                }

                //  Player Turn Check
                for (int j = 0; j < player.length; j++) {
                    if (j < player.length - 1) {
                        levelWriter.write(player[j].getPlayerTurn() + ",");
                    } else {
                        levelWriter.write(player[j].getPlayerTurn() + "\n");
                    }
                }

                //  ENTIRE BOARD
                for (int j = 0; j < board.getRowSize(); j++) {
                    for (int k = 0; k < board.getColumnSize(); k++) {
                        //  is this the last board in the array
                        if (i == levelArray.size() - 1) {
                            //  Last element in last board array
                            if (j == board.getRowSize() - 1 &&
                                    k == board.getColumnSize() - 1) {

                                levelWriter.write(j + "," + k + "," + board.getTileFromBoard(j, k).getType()
                                        + "," + board.getTileFromBoard(j, k).getOrientation() + ",Normal,"
                                        + board.getTileFromBoard(j, k).isFixed());
                            } else {
                                levelWriter.write(j + "," + k + "," + board.getTileFromBoard(j, k).getType()
                                        + "," + board.getTileFromBoard(j, k).getOrientation() + ",Normal,"
                                        + board.getTileFromBoard(j, k).isFixed() + "\n" + "");
                            }

                        } else {
                            levelWriter.write(j + "," + k + "," + board.getTileFromBoard(j, k).getType()
                                    + "," + board.getTileFromBoard(j, k).getOrientation() + ",Normal,"
                                    + board.getTileFromBoard(j, k).isFixed() + "\n");
                        }

                    }
                }
            } catch (IOException e) {
                System.out.println("Reading File Error Has Occured");
                e.printStackTrace();
            }
        }
    }

    /**
     * Takes in ArrayList of profiles.
     *
     * @param profileArray takes in an ArrayList of profiles
     */
    public static void createNewProfile(ArrayList<Profile> profileArray) {
        //  Clear the file
        try (PrintWriter dumpFile = new PrintWriter("Profiles.txt")) {
            dumpFile.print("");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        for (Profile profile : profileArray) {
            try (FileWriter profileWriter = new FileWriter("Profiles.txt", true)) {

                profileWriter.write(profile.getProfileName() + "\n");
                profileWriter.write(profile.getWinCount() + "\n");
                profileWriter.write(profile.getLoseCount() + "\n");

            } catch (IOException e) {
                System.out.println("createNewProfile error has occurred");
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads the data file used by the program, and returns a constructed arraylist.
     *
     * @param in the scanner of the file
     * @return the arraylist represented by the data file
     */

    private static ArrayList<Profile> readDataFileProfile(Scanner in) {
        ArrayList<Profile> returnableArray = new ArrayList<>();

        while (in.hasNext()) {
            try {
                Profile profile = loadProfile(in);
                returnableArray.add(profile);
            } catch (Error e) {
                System.out.print("An unknown error has occurred.");
            }
        }
        in.close();
        return returnableArray;
    }

    /**
     * Method to read and turn an arraylist of profiles from this file. The
     * program should handle the file not found exception here and shut down
     * the program elegantly.
     *
     * @param filename the name of the file
     * @return the ArrayList of Profiles from the file.
     */

    public static ArrayList<Profile> readProfileDataFile(String filename) {
        File inputFile = new File(filename);

        Scanner in = null;
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
            System.out.print(e);
            System.exit(0);
        }
        return FileManager.readDataFileProfile(in);
    }

    /**
     * Reads the data file used by the program, and returns a constructed arraylist.
     *
     * @param in the scanner of the file
     * @param loadType refers to whether its a new file or saved file
     * @return the arraylist represented by the data file
     */

    private static ArrayList<Level> readDataFileLevel(Scanner in, String loadType) {
        ArrayList<Level> returnableArray = new ArrayList<>();
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
     * Method to read and turn an arraylist of level from this file. The
     * program should handle the file not found exception here and shut down
     * the program elegantly
     *
     * @param filename the name of the file
     * @param type refers to whether its a new file or saved file
     * @return the ArrayList of Profiles from the file.
     */

    public static ArrayList<Level> readLevelDataFile(String filename, String type) {
        File inputFile = new File(filename);
        Scanner in = null;
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
            System.exit(0);
        }
        return FileManager.readDataFileLevel(in, type);
    }

    /**
     * Used to create floor tiles for board.
     *
     * @param tile a string representation of the tile being created
     * @param orientation of the tile
     * @param state of the tile
     * @param isFixed status of fixed state
     * @return a Floor Tile object
     */

    private static FloorTile createTempTile(String tile, int orientation, String state, Boolean isFixed) {

        FloorTile tempTile = null;
        switch (tile) {
            case "Straight":
                tempTile = new StraightTile(orientation, state, isFixed);
                break;
            case "TShaped":
                tempTile = new TShapedTile(orientation, state, isFixed);
                break;
            case "Corner":
                tempTile = new CornerTile(orientation, state, isFixed);
                break;
            case "Goal":
                tempTile = new GoalTile(orientation, state, isFixed);
                break;
            default:
                System.out.println("An error has occurred whoops");
        }
        return tempTile;
    }

    /**
     * Used to create Tiles for player Inventory.
     *
     * @param tile a string representation of the tile being created
     * @param orientation of the tile
     * @return  a Tile object
     */

    public static Tile createPlayerInventoryTiles(String tile, int orientation) {
        Tile tempTile = null;

        switch (tile) {
            case "Straight":
                tempTile = new StraightTile(orientation, "normal", false);
                break;
            case "TShaped":
                tempTile = new TShapedTile(orientation, "normal", false);
                break;
            case "Corner":
                tempTile = new CornerTile(orientation, "normal", false);
                break;
            case "Goal":
                tempTile = new GoalTile(orientation, "normal", false);
                break;
            case "Fire":
                tempTile = new FireTile();
                break;
            case "Ice":
                tempTile = new IceTile();
                break;
            case "DoubleMove":
                tempTile = new DoubleMoveTile();
                break;
            case "BackTrack":
                tempTile = new BacktrackTile();
                break;
            default:
                System.out.println("Why hello there ;)");
        }
        return tempTile;
    }

    /**
     * Method to convert a string into a string array, with a particular delimiter.
     *
     * @param a the string
     * @return an array with the contents of the string
     */

    private static String[] stringToStringArray(String a) {
        return a.split("[,]");
    }

    /**
     * Method takes a String, converts it into an Integer, and handles
     * the NumberFormatException here and shut down the program elegantly.
     *
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
     * Method takes a String, converts it into a Integer array, and handles
     * the NumberFormatException here and shut down the program elegantly.
     *
     * @param string the string
     * @return an array of type int with the contents of the string
     */

    private static int[] stringToIntArray(String string) {
        String[] item = string.split("[,]");

        int[] returnVal = new int[item.length];

        for (int i = 0; i < item.length; i++) {
            try {
                returnVal[i] = Integer.parseInt(item[i]);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
        return returnVal;
    }

    /**
     * Method takes a String, converts it into a Boolean array, and handles
     * the Exception here and shut down the program elegantly.
     *
     * @param string the string
     * @return an array of type int with the contents of the string
     */

    private static Boolean[] stringToBooleanArray(String string) {
        String[] item = string.split("[,]");

        Boolean[] returnVal = new Boolean[item.length];

        for (int i = 0; i < item.length; i++) {
            try {
                returnVal[i] = Boolean.parseBoolean(item[i]);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return returnVal;
    }

}
