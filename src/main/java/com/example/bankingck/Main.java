package com.example.bankingck;

//import com.example.bankingck.Client.Controller.User_loginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("SignIN.fxml"));
        Parent root = loader.load() ;

//        User_loginController controller = loader.getController() ;
//        controller.setPrevStage(stage); // Cung cấp stage chính cho controller

        Scene scene = new Scene(root) ;
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}