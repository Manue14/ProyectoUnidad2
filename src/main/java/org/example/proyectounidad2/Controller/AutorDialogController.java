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
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

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

    FileChooser fileChooser=new FileChooser();
    Autor autor;
    private byte[] currentImage;
    boolean modo; //True->Añadir False->Modificar
    
    @FXML
    void subirArchivo(MouseEvent event) {
        try {
            File selectedFile= fileChooser.showOpenDialog(new Stage());
            if (selectedFile !=null){
                //Image imagenSeleccionada = new Image(selectedFile.toURI().toString());
                this.currentImage = Files.readAllBytes(selectedFile.toPath());

                String mimeType = "image/png";
                // Convert to Base64 and use it in a Data URL
                String base64Image = Base64.getEncoder().encodeToString(this.currentImage);
                String dataUrl = "data:" + mimeType + ";base64," + base64Image;
                ap_imagenHolder.setStyle(
                        "-fx-background-image: url('" + dataUrl + "');"+
                                "-fx-background-size: contain;" +
                                "-fx-background-repeat: no-repeat;" +
                                "-fx-background-position: right center;"
                );
                //setAnchorPaneBackground(imagenSeleccionada);
            }
        } catch (IOException exception) {
            System.out.println("Error al convertir la imagen seleccionada a un array de bytes: " + exception.getMessage());
        } 
    }

    public void initialize() {
        fileChooser.setInitialDirectory(new File("C:\\"));
        //fileChooser.setInitialDirectory(new File("/home/manu/Descargas"));
        fileChooser.setTitle("Cargar imagen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".jpg","*.jpg"),
                new FileChooser.ExtensionFilter(".png","*.png"),new FileChooser.ExtensionFilter("All images","*.jpg","*.png"));

        Platform.runLater(() -> {
            if (!modo) {
                lbl_titulodlg.setText("Modificar autor");
                cargarDatosAutor();
            }else{
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

            String mimeType = "image/png";
            // Convert to Base64 and use it in a Data URL
            String base64Image = Base64.getEncoder().encodeToString(autor.getFoto());
            String dataUrl = "data:" + mimeType + ";base64," + base64Image;
            //<div style="background:url( data:image/jpeg;base64,@Convert.ToBase64String(electedOfficial.Picture) )"></div>
            ap_imagenHolder.setStyle(
                    "-fx-background-image: url('" + dataUrl + "');"+
                            "-fx-background-size: contain;" +
                            "-fx-background-repeat: no-repeat;" +
                            "-fx-background-position: right center;"
            );
            /*ap_imagenHolder.setBackground(
                    new Background(
                            new BackgroundImage(
                                    new Image(new ByteArrayInputStream(autor.getFoto())),
                                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT , BackgroundPosition.CENTER, BackgroundSize.DEFAULT
                            )
                    )
            );*/
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
            
            autor.setFoto(this.currentImage);
            
            if (dbConnector.updateAutor(autor) == true) {
                System.out.println("*****--------AUTOR ACTUALIZADA CON ÉXITO----------------************");
            }

        } catch (Exception e) {
            System.out.println("Error modificando el autor: " + e.getMessage());
        }
    }
    
    public void createAutor() {
        Autor autor = new Autor();
        
        autor.setNombre(tf_nombre.getText());
        autor.setApellido1(tf_apellido1.getText());
        autor.setApellido2(tf_apellido2.getText());
        autor.setNacionalidad(tf_nacionalidad.getText());
        autor.setNacimiento(dp_nacimiento.getValue());
        autor.setFallecimiento(dp_fallecimiento.getValue());
        autor.setFoto(this.currentImage);
            
        if (dbConnector.createAutor(autor) != null) {
            System.out.println("*****--------AUTOR CREADA CON ÉXITO----------------************");
        }
    }
    
    public void setAnchorPaneBackground(Image img) {
        ap_imagenHolder.setBackground(
                new Background(
                        new BackgroundImage(
                                img,
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
                        )
                )
        );
    }

}
