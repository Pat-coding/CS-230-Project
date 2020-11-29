package layout;

//import backend.Board;
import Tiles.FloorTile;
import backend.Board;
import backend.GameFlow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;


import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML
    private GridPane topGrid;
    @FXML
    private GridPane rightGrid;
    @FXML
    private GridPane bottomGrid;
    @FXML
    private GridPane leftGrid;
    @FXML
    private GridPane tileGrid;
    int boardHeight = 5;
    int boardWidth = 5;
    int fixedcount1 =0;
    int fixedcount2 =0;
    int fixedcount3 =0;
    int fixedcount4 =0;

    int[] fixedTilesx ={1,2};
    int[] fixedTilesy ={1,3};
    int size = 100;
    ArrayList<Button> buttons =  new ArrayList<>();
    GameFlow gameFlow;
    FloorTile selectedTile;
    Board currentBoard;


    Image road = new Image(getClass().getResourceAsStream("/resources/roadDown.jpeg")); //testing image from internet
    Image straight = new Image(getClass().getResourceAsStream("/resources/STRAIGHT_PLACEHOLDER.png"));
    Image corner = new Image(getClass().getResourceAsStream("/resources/CORNER_PLACEHOLDER.png"));
    Image goal = new Image(getClass().getResourceAsStream("/resources/GOAL_PLACEHOLDER.png"));
    Image arrowDown = new Image(getClass().getResourceAsStream("/resources/arrowDOWN.png"));
    Image arrowUp = new Image(getClass().getResourceAsStream("/resources/arrowUP.png"));
    Image arrowLeft = new Image(getClass().getResourceAsStream("/resources/arrowLeft.png"));
    Image arrowRight = new Image(getClass().getResourceAsStream("/resources/arrowRight.png"));


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupBoard();
        setupArrows();
        //setActions();
    }

    public void setupBoardOLD(){
        for (int x = 0; x < boardWidth; x++) { //creates 5x5 board with selected image (need to put random images)
            for (int y = 0; y < boardHeight; y++) {
                ImageView tileImg = new ImageView();
                tileImg.setFitHeight(size);
                tileImg.setFitWidth(size);
                tileImg.setImage(road); //get images from save file here
                tileGrid.add(tileImg, x,y);
            }
        }
    }
    public void setupBoard(){
        int fixedcount = 0;
        for (int x = 0; x < boardWidth; x++) { //creates 5x5 board with selected image (need to put random images)
            for (int y = 0; y < boardHeight; y++) {
                if(fixedcount < fixedTilesx.length) {       //checks if any fixed tiles are left
                    if (fixedTilesx[fixedcount] == x && fixedTilesy[fixedcount] == y) {  //checks if fixed tiles at current coords
                        insertTile(goal,x,y);
                        fixedcount++;

                    } else {
                        insertTile(road,x,y);   // inserts normal tile if there are still fixed tiles left
                    }
                }else{
                    insertTile(road,x,y);   // inserts normla tile if there are no fixed tiles left

                }
            }
        }
    }
    public void insertTile(Image tiletype,int x ,int y){
        ImageView tileImg = new ImageView();
        tileImg.setFitHeight(size);
        tileImg.setFitWidth(size);
        tileImg.setImage(tiletype); //get images from save file here
        tileGrid.add(tileImg, x, y);

    }



    //Need to set arrows depending if tile is fixed or not
    //also need to create clickable arrows.
    public void setupArrows() {

        topGrid.setTranslateX(size);
        bottomGrid.setTranslateX(size);
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++){
                if (x==0) {
                    Button buttonArrow = new Button();
                    ImageView tileImg = new ImageView();
                    buttonArrow.setTranslateX(x);
                    buttonArrow.setTranslateY(y);
                    buttonArrow.setMinSize(size,size);
                    buttonArrow.setMaxSize(size,size);
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setPreserveRatio(true);
                    tileImg.setImage(arrowRight);
                    buttonArrow.setGraphic(tileImg);
                    buttons.add(buttonArrow);
                    //leftGrid.add(tileImg, x,y);
                    leftGrid.add(buttonArrow,x,y);
                    //topGrid.add(buttonArrow, x, y);
                    if(fixedcount1 < fixedTilesx.length) {       //checks if any fixed tiles are left
                        if (fixedTilesy[fixedcount1] == y) {
                            buttonArrow.setVisible(false);
                            fixedcount1++;

                        }
                    }

                } else if (x == boardWidth-1){ //4 is the board size, we will get board size from save files, this is just for testing right now.
                    Button buttonArrow = new Button();
                    ImageView tileImg = new ImageView();
                    buttonArrow.setTranslateX(x);
                    buttonArrow.setTranslateY(y);
                    buttonArrow.setMinSize(size,size);
                    buttonArrow.setMaxSize(size,size);
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setPreserveRatio(true);
                    tileImg.setImage(arrowLeft);
                    buttonArrow.setGraphic(tileImg);
                    buttons.add(buttonArrow);
                    //rightGrid.add(tileImg, x,y);
                    rightGrid.add(buttonArrow,x,y);
                    //topGrid.add(buttonArrow, x, y);
                    if(fixedcount2 < fixedTilesx.length) {       //checks if any fixed tiles are left
                        if (fixedTilesy[fixedcount2] == y) {
                            buttonArrow.setVisible(false);
                            fixedcount2++;

                        }
                    }
                }


                if (y==0){
                    ImageView tileImg = new ImageView();
                    Button buttonArrow = new Button();
                    buttonArrow.setTranslateX(x);
                    buttonArrow.setTranslateY(y);
                    buttonArrow.setMinSize(size, size);
                    buttonArrow.setMaxSize(size, size);
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setPreserveRatio(true);
                    tileImg.setImage(arrowUp);
                    buttons.add(buttonArrow);
                    buttonArrow.setGraphic(tileImg);
                    //bottomGrid.add(tileImg, x,y);
                    bottomGrid.add(buttonArrow, x, y);
                    //topGrid.add(buttonArrow, x, y);
                    if(fixedcount3 < fixedTilesx.length) {       //checks if any fixed tiles are left
                        if (fixedTilesx[fixedcount3] == x) {
                            buttonArrow.setVisible(false);
                            fixedcount3++;

                        }
                    }


                } else if (y==boardHeight-1){ //4 is the board size, we will get board size from save files, this is just for testing right now.
                    ImageView tileImg = new ImageView();
                    Button buttonArrow = new Button();
                    buttonArrow.setTranslateX(x);
                    buttonArrow.setTranslateY(y);
                    buttonArrow.setMinSize(size, size);
                    buttonArrow.setMaxSize(size, size);
                    buttonArrow.setGraphic(tileImg);
                    tileImg.setFitHeight(size);
                    tileImg.setFitWidth(size);
                    tileImg.setPreserveRatio(true);
                    buttons.add(buttonArrow);
                    tileImg.setImage(arrowDown);
                    //topGrid.add(tileImg, x,y);
                    topGrid.add(buttonArrow, x, y);
                    if(fixedcount4 < fixedTilesx.length) {       //checks if any fixed tiles are left
                        if (fixedTilesx[fixedcount4] == x) {
                            buttonArrow.setVisible(false);
                            fixedcount4++;

                        }
                    }
                }
            }

        }




    }


    private void setActions(){
        buttons.get(0).setOnAction(e -> pushRight(0,0));

        buttons.get(1).setOnAction(e -> pushup(0,4));

        buttons.get(2).setOnAction(e -> pushRight(0,1));

        buttons.get(3).setOnAction(e -> pushRight(0,2));

        buttons.get(4).setOnAction(e -> pushRight(0,3));

        buttons.get(5).setOnAction(e -> pushRight(0,4));

        buttons.get(6).setOnAction(e -> pushdown(0,0));

        buttons.get(7).setOnAction(e -> pushup(1,4));

        buttons.get(8).setOnAction(e -> pushdown(1,0));

        buttons.get(9).setOnAction(e -> pushup(2,4));

        buttons.get(10).setOnAction(e -> pushdown(2,4));

        buttons.get(11).setOnAction(e -> pushup(3,4));

        buttons.get(12).setOnAction(e -> pushdown(3,0));

        buttons.get(13).setOnAction(e -> pushLeft(4,0));

        buttons.get(14).setOnAction(e -> pushup(4,4));

        buttons.get(15).setOnAction(e -> pushLeft(4,1));

        buttons.get(16).setOnAction(e -> pushLeft(4,2));

        buttons.get(17).setOnAction(e -> pushLeft(4,3));

        buttons.get(18).setOnAction(e -> pushLeft(4,4));

        buttons.get(19).setOnAction(e -> pushdown(4,0));

    }
    public void pushLeft(int x,int y){
        //(Board.Cardinals direction, FloorTile tile, int x, int y)
        Board.Cardinals left = Board.Cardinals.LEFT;
        System.out.println("P L");
        gameFlow.playerSlotFloorTile(left,selectedTile,x,y);
        int discardx = x-4;
        int discardy = y;


    }
    public void pushRight(int x,int y){
        Board.Cardinals right = Board.Cardinals.RIGHT;
        System.out.println("P R");
        gameFlow.playerSlotFloorTile(right,selectedTile,x,y);
        //shift tiles
        int discardx = x+4;
        int discardy = y;
    }
    public void pushup(int x,int y){
        Board.Cardinals bottom = Board.Cardinals.BOTTOM;
        System.out.println("P U");
        gameFlow.playerSlotFloorTile(bottom,selectedTile,x,y);
        //shift tiles
        int discardx = x;
        int discardy = y+4;
    }
    public void pushdown(int x,int y){
        Board.Cardinals top = Board.Cardinals.TOP;
        System.out.println("P D");
        gameFlow.playerSlotFloorTile(top,selectedTile,x,y);
        //shift tiles
        int discardx = x;
        int discardy = y-4;
    }

    private boolean checkinlist(int[] arr, int key){
        boolean check = false;
        for(int i = 0; i < arr.length; i++){
            if (arr[i] == key) {
                check = true;
                break;
            }
        }
        return check;
    }
}








