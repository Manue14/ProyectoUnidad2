package org.example.proyectounidad2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.proyectounidad2.Controller.HomeController;
import org.example.proyectounidad2.Model.DBConnector;
import org.example.proyectounidad2.Model.Table;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {

    private static Scene scene;
    private static Stage stage;

    public static DBConnector dbConnector;
    public static int obraDialogMode;//0-> añadir obra 1->Modificar obra

    static {
        try {
            dbConnector = new DBConnector();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("acceso.fxml"));
        scene = new Scene(fxmlLoader.load(), 500, 320);

        Image icon= new Image(getClass().getResource("/Icon/icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Museo Arte");
        stage.setScene(scene);
        //Cerral conexion bd
        stage.setOnCloseRequest(event -> {
            dbConnector.close();

        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }




    public static void setRoot(String fxml) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(fxml + ".fxml"));
        stage.setTitle("Crear usuario");

        if(fxml.equals("home")){

            stage.setMaximized(true);


        } else if (fxml.equals("acceso")) {
            stage.setWidth(500);
            stage.setHeight(320);
        } else if (fxml.equals("home")) {

            stage.setMaximized(true);
            {

            }
        } else if (fxml.equals("user")) {
            stage.setWidth(600);
            stage.setHeight(550);

        } else if (fxml.equals("admin")) {
            stage.setMaximized(true);
        } else if (fxml.equals("obraDialog")) {
            stage.setWidth(613);
            stage.setHeight(419);
        }


        stage.setOpacity(0);
        scene.setRoot(root);

        //stage.setWidth(700);
        //stage.setHeight(650);

        stage.centerOnScreen();
        stage.setOpacity(1);
    }
}