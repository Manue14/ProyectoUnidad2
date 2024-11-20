package org.example.proyectounidad2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Scene scene;
    private static Stage stage;


    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin.fxml"));
        scene = new Scene(fxmlLoader.load(), 500, 320);

        Image icon= new Image(getClass().getResource("/Icon/icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Museo Arte");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }




    public static void setRoot(String fxml) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(fxml + ".fxml"));
        stage.setTitle("Crear usuario");

        stage.setOpacity(0);
        scene.setRoot(root);

        //stage.setWidth(700);
        //stage.setHeight(650);

        stage.centerOnScreen();
        stage.setOpacity(1);
    }
}