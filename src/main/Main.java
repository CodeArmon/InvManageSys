package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/** This class creates an Inventory Management app that displays messages. The javadoc is located in the javadoc folder under the src package. The future enhancement comment can be located in MainScreen Controller. The runtime error comment can be located in AddPart Class Controller.*/
public class Main extends Application {

    /**This method loads and sets the main screen. */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
    /** This is the main method. This is the main method that gets called when you run the program.
     @param args Launch args */
    public static void main(String[] args){
        launch(args);
    }

}
