package org.example.proyectounidad2.Controller;

import java.io.ByteArrayInputStream;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.proyectounidad2.Model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import static org.example.proyectounidad2.HelloApplication.dbConnector;

public class ObraDialogController {

    @FXML
    private AnchorPane ap_imagenHolder;

    @FXML
    private Button btn_subirArchivo;

    @FXML
    private CheckBox chk_popular;

    @FXML
    private ComboBox<Categoria> cmb_categoria;

    @FXML
    private ComboBox<Departamento> cmb_departamento;

    @FXML
    private ComboBox<Movimiento> cmb_movimiento;

    @FXML
    private ComboBox<Autor> cmb_autor;

    @FXML
    private Label lbl_titulodlg;

    @FXML
    private TextField tf_fecha;

    @FXML
    private TextField tf_medidas;

    @FXML
    private TextField tf_medio;

    @FXML
    private TextField tf_titulo;

    @FXML
    FileChooser fileChooser = new FileChooser();

    Obra obra;
    private byte[] currentImage;
    boolean modo; //True->Añadir False->Modificar

    @FXML
    void subirArchivo(MouseEvent event) {
        try {
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                Image imagenSeleccionada = new Image(selectedFile.toURI().toString());
                this.currentImage = Files.readAllBytes(selectedFile.toPath());
                setAnchorPaneBackground(imagenSeleccionada);
            }
        } catch (IOException exception) {
            System.out.println("Error al convertir la imagen seleccionada a un array de bytes: " + exception.getMessage());
        }
    }

    public void initialize() {
        cargarCmbs();
        fileChooser.setInitialDirectory(new File("C:\\"));
        //fileChooser.setInitialDirectory(new File("/home/manu/Descargas"));
        fileChooser.setTitle("Cargar imagen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".jpg", "*.jpg"),
                new FileChooser.ExtensionFilter(".png", "*.png"), new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png"));


        Platform.runLater(() -> {
            if (!modo) {
                lbl_titulodlg.setText("Modificar obra");
                cargarDatosObra();
            }
            {
                lbl_titulodlg.setText("Añadir obra");

            }
        });

    }

    public void setObra(Obra obra) {
        this.obra = obra;

    }

    public void setModo(boolean bool) {
        this.modo = bool;
    }

    public void getObra() {
        /*obra.setTitulo(tf_titulo.getText());
        obra.setA(tf_autor.getText());
        obra.set(tf_medidas.getText());
        obra.set(tf_medio.getText());
        obra.set(tf_fecha.getText());

        cmb_categoria.setValue(obra.getCategoria());
        cmb_movimiento.setValue(dbConnector.getMovimientoById(obra.getId_movimiento()));
        cmb_departamento.setValue(dbConnector.getDepartamentoById(obra.getId_departamento()));

        chk_popular.setSelected(obra.isPopular());*/

    }

    private void cargarCmbs() {

        cmb_categoria.getItems().clear();
        cmb_categoria.getItems().add(null);
        for (Categoria categoria : Categoria.values()) {
            cmb_categoria.getItems().add(categoria);
        }

        cmb_departamento.getItems().clear();
        cmb_departamento.getItems().add(null);
        ArrayList<Departamento> departamentos = dbConnector.getAllDepartamentos();
        for (Departamento departamento : departamentos) {
            cmb_departamento.getItems().add(departamento);
        }

        cmb_movimiento.getItems().clear();
        cmb_movimiento.getItems().add(null);
        ArrayList<Movimiento> movimientos = dbConnector.getAllMovimientos();
        for (Movimiento movimiento : movimientos) {
            cmb_movimiento.getItems().add(movimiento);
        }

        cmb_autor.getItems().clear();
        ArrayList<Autor> autores = dbConnector.getAllAutores();
        for (Autor autor : autores) {
            cmb_autor.getItems().add(autor);
        }

    }


    public void cargarDatosObra() {
        tf_titulo.setText(obra.getTitulo());
        tf_medidas.setText(obra.getMedidas());
        tf_medio.setText(obra.getMedio());
        tf_fecha.setText(obra.getFecha());

        cmb_categoria.setValue(obra.getCategoria());
        cmb_movimiento.setValue(dbConnector.getMovimientoById(obra.getId_movimiento()));
        cmb_departamento.setValue(dbConnector.getDepartamentoById(obra.getId_departamento()));
        cmb_autor.setValue(dbConnector.getAutorById(obra.getId_autor()));

        chk_popular.setSelected(obra.isPopular());
        if (obra.getImg()!=null){
            setAnchorPaneBackground(new Image(new ByteArrayInputStream(obra.getImg())));
            this.currentImage = obra.getImg();
        }

        /*ap_imagenHolder.setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(new ByteArrayInputStream(obra.getImg())),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
                        )
                )
        );*/
        /*ap_imagenHolder.setStyle(
                        "-fx-background-size: contain;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-position: right center;"
        );*/
    }

    public void modificarObra() {
        try {
            System.out.println("-------MODIFICANDO OBRA---------------");
            obra.setTitulo(tf_titulo.getText());
            String[] parts = tf_medidas.getText().split("x");//Dividiendo medidas en alto y ancho
            obra.setAlto(Float.parseFloat(parts[0]));
            obra.setAncho(Float.parseFloat(parts[1]));

            obra.setMedio(tf_medio.getText());
            obra.setFecha(tf_fecha.getText());

            // Recoger datos de los ComboBoxes
            obra.setCategoria(cmb_categoria.getValue()); // Obtiene la selección como enum
            obra.setId_movimiento(cmb_movimiento.getValue().getId());
            obra.setId_departamento(cmb_departamento.getValue().getId());
            obra.setId_autor(cmb_autor.getValue().getId());

            // Recoger estado del CheckBox
            obra.setPopular(chk_popular.isSelected());

            obra.setImg(this.currentImage);

            //Hay que cambiarlo para que los consulte en la obra
            // Imprimir datos recogidos para verificar
            /*System.out.println("Título: " + titulo);
            System.out.println("Autor: " + autor);
            System.out.println("Medidas: " + medidas);
            System.out.println("Medio: " + medio);
            System.out.println("Fecha: " + fecha);
            System.out.println("Categoría: " + categoria);
            System.out.println("Movimiento: " + (movimiento != null ? movimiento.getNombre() : "N/A"));
            System.out.println("Departamento: " + (departamento != null ? departamento.getNombre() : "N/A"));
            System.out.println("Popular: " + popular);
            System.out.println("Imagen URL: " + imgPath);*/

            // Aquí puedes usar los datos para actualizar la obra en la base de datos o realizar otras operaciones.
            if (dbConnector.updateObra(obra) == true) {
                System.out.println("*****--------OBRA ACTUALIZADA CON ÉXITO----------------************");
            }
        } catch (Exception e) {
            System.out.println("Error modificando la obra: " + e.getMessage());
        }
    }

    public void createObra() {
        try {
            clearErrorStyles();

            boolean isValid = true;

            if (tf_titulo.getText().isEmpty()) {
                tf_titulo.getStyleClass().add("error");
                isValid = false;
            }
            if (tf_fecha.getText().isEmpty()) {
                tf_fecha.getStyleClass().add("error");
                isValid = false;
            }
            if (tf_medidas.getText().isEmpty() || !tf_medidas.getText().contains("x")) {
                tf_medidas.getStyleClass().add("error");
                isValid = false;
            }
            if (cmb_autor.getValue() == null) {
                cmb_autor.getStyleClass().add("error");
                isValid = false;
            }
            if (cmb_categoria.getValue() == null) {
                cmb_categoria.getStyleClass().add("error");
                isValid = false;
            }
            if (cmb_departamento.getValue() == null) {
                cmb_departamento.getStyleClass().add("error");
                isValid = false;
            }

            if (isValid) {
                Obra obra = new Obra();

                obra.setTitulo(tf_titulo.getText());
                String[] parts = tf_medidas.getText().split("x");//Dividiendo medidas en alto y ancho
                obra.setAlto(Float.parseFloat(parts[0]));
                obra.setAncho(Float.parseFloat(parts[1]));
                obra.setMedio(tf_medio.getText());
                obra.setFecha(tf_fecha.getText());
                obra.setCategoria(cmb_categoria.getValue());
                if (cmb_movimiento.getValue() != null) {
                    obra.setId_movimiento(cmb_movimiento.getValue().getId());
                } else {
                    obra.setId_movimiento(-1); // Valor predeterminado que indica "sin movimiento"
                }
                obra.setId_departamento(cmb_departamento.getValue().getId());
                obra.setId_autor(cmb_autor.getValue().getId());
                obra.setPopular(chk_popular.isSelected());
                if (this.currentImage != null) {
                    obra.setImg(this.currentImage);
                } else {
                    // Opcionalmente, podrías establecer una imagen predeterminada o dejarla en blanco
                    obra.setImg(null); // Si la propiedad lo permite
                }
                obra.setDescripcion("test");

                if (dbConnector.createObra(obra) != null) {
                    System.out.println("*****-----OBRA CREADA CON ÉXITO----------************");
                }
            }
        } catch (Exception exception) {
            System.out.println("Error al crear una nueva obrea: " + exception.getMessage());
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

    private void clearErrorStyles() {
        tf_titulo.getStyleClass().remove("error");
        tf_fecha.getStyleClass().remove("error");
        tf_medidas.getStyleClass().remove("error");
        cmb_autor.getStyleClass().remove("error");
        cmb_categoria.getStyleClass().remove("error");
        cmb_departamento.getStyleClass().remove("error");
    }


}
