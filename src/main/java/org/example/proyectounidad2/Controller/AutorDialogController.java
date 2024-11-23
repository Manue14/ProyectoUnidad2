package org.example.proyectounidad2.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.proyectounidad2.Model.Autor;
import org.example.proyectounidad2.Model.Obra;

import java.io.ByteArrayInputStream;
import java.io.File;

import static org.example.proyectounidad2.HelloApplication.dbConnector;

public class AutorDialogController {

    @FXML
    private AnchorPane ap_imagenHolder;

    @FXML
    private Button btn_subirArchivo;

    @FXML
    private DatePicker dp_fallecimiento;

    @FXML
    private DatePicker dp_nacimiento;

    @FXML
    private Label lbl_titulodlg;

    @FXML
    private TextField tf_apellido1;

    @FXML
    private TextField tf_apellido2;

    @FXML
    private TextField tf_nacionalidad;

    @FXML
    private TextField tf_nombre;

    @FXML
    void subirArchivo(MouseEvent event) {
        File selectedFile= fileChooser.showOpenDialog(new Stage());
        if (selectedFile !=null){
            Image imagenSeleccionada = new Image(selectedFile.getPath());
            //Hacer algo con la imagen seleccionada
        }
    }

    FileChooser fileChooser=new FileChooser();
    Autor autor;
    boolean modo; //True->Añadir False->Modificar

    public void initialize() {
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.setTitle("Cargar imagen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".jpg","*.jpg"),
                new FileChooser.ExtensionFilter(".png","*.png"),new FileChooser.ExtensionFilter("All images","*.jpg","*.png"));

        Platform.runLater(() -> {
            if (!modo) {
                lbl_titulodlg.setText("Modificar autor");
                cargarDatosAutor();
            }{
                lbl_titulodlg.setText("Añadir autor");

            }
        });

    }

    public void setAutor(Autor autor) {
        this.autor = autor;

    }

    public void setModo(boolean bool) {
        this.modo = bool;
    }

    public void cargarDatosAutor() {
        tf_nombre.setText(autor.getNombre());
        tf_apellido1.setText(autor.getApellido1()==null?" ":autor.getApellido1());
        tf_apellido2.setText(autor.getApellido2()==null?" ":autor.getApellido2());
        tf_nacionalidad.setText(autor.getNacionalidad());

        dp_nacimiento.setValue(autor.getNacimiento());
        dp_fallecimiento.setValue(autor.getFallecimiento());

        if(autor.getFoto()!=null){
            ap_imagenHolder.setBackground(
                    new Background(
                            new BackgroundImage(
                                    new Image(new ByteArrayInputStream(autor.getFoto())),
                                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
                            )
                    )
            );
        }

        /*ap_imagenHolder.setStyle(
                        "-fx-background-size: contain;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-position: right center;"
        );*/
    }

    public void modificarAutor() {
        try {
            System.out.println("-------MODIFICANDO AUTOR---------------");
            autor.setNombre(tf_nombre.getText());
            autor.setApellido1(tf_apellido1.getText());
            autor.setApellido2(tf_apellido2.getText());
            autor.setNacionalidad(tf_nacionalidad.getText());

            autor.setNacimiento(dp_nacimiento.getValue());
            autor.setFallecimiento(dp_fallecimiento.getValue());

            // Imprimir datos recogidos para verificar
            autor.getAllInfo();

        } catch (Exception e) {
            System.out.println("Error modificando la obra: " + e.getMessage());
        }


    }

}
