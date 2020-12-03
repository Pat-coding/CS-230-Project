package backend;

import Tiles.CornerTile;
import Tiles.FloorTile;

import java.io.File;
import java.util.ArrayList;

public class FileTester {

    public static ArrayList<Profile> profiles;
    public static ArrayList<Level> savedLevels;
    public static ArrayList<Level> newLevels;

    public static void main(String[] arg) {
        try {
            savedLevels = FileManager.readLevelDataFile("SavedLevel.txt", "Saved Level");
            Player[] player = savedLevels.get(0).getPlayerData();
            Player[] players = savedLevels.get(1).getPlayerData();

            System.out.println(player[0].getPlayerInventory());
            System.out.println(players[0].getPlayerInventory());

            System.out.println(savedLevels.get(0).getBoardData());
            System.out.println(savedLevels.get(1).getBoardData());
            System.out.println(savedLevels.get(0).getBoardData().getTileFromBoard(0,0).getType());
            System.out.println(savedLevels.get(1).getBoardData().getTileFromBoard(0,0).getType());
            System.out.println(savedLevels.get(1).getPlayerData()[0].getPlayerInventory());
            System.out.println(savedLevels.get(1).getPlayerData()[0].getPlayerCordX());
            System.out.println(savedLevels.get(1).getPlayerData()[0]);

            System.out.println(savedLevels.get(1).getPlayerData()[0].getTileHand());

        } catch (Error e) {
            System.out.println("FML");
        }
    }
}

