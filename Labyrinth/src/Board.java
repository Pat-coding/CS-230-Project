/**
 * @author Pat
 * @version 1.0
 */

public class Board {

    private Points[] tileCoordinates;
    private Points[] playerCoordinates;
    private int orientation; // 90, 180, 270
    private int[][] boardSize;

    private void initiliseSpawnPoints(Points[] playerCoordinates) {
        this.playerCoordinates = playerCoordinates;
        //JavaFX animation code here
    }

    private void initiliseTiles(Points[] tileCoordinates) {
        this.tileCoordinates = tileCoordinates;
        //JavaFX animation code here
    }

    private boolean checkTilePlacementRow(Points[] tileCoordinates) {
//        for (int i = 0; i < boardSize.; i++) {
//            if (tileCoordinates[i,boardY].fixed == true || tile.frozen == true) {
//                return false;
//            } else {
//                tileCoordinates
//            }
//        }
    }

    private boolean checkTilePlacementCol(int y) {
//        for (int i = 0; i < boardY; i++) {
            // if (tile.fixed == true || tile.frozen == true){return false} something like this, no clue XD
        }

    private void discardTilesToSilkBag() {

    }

    private void setOrientation(int orientation) {

    }
}
