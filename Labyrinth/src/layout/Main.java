package layout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;

    //Used to load the fxml files, all logical modifications to Main Menu must be done on MainMenu class.
    //This file can only be used to change the resolution and the title.
    //do not change the code except resolution and title.
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Labyrinth");
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("layout/MainMenu.fxml").openStream());
            MainMenu menu = loader.getController();
            menu.setStage(primaryStage);
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
