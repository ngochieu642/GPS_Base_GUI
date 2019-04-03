package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 483));
        primaryStage.setResizable(false); //Fix size window
        primaryStage.show();
        } catch (Exception e){
            System.out.println("Oops there are Errors!");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
