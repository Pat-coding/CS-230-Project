package layout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //Used to load the fxml files, all logical modifications to Main Menu must be done on MainMenu class.
    //This file can only be used to change the resolution and the title.
    //do not change the code except resolution and title.
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("Labyrinth");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
        primaryStage.sizeToScene();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
