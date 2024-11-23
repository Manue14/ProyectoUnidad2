package org.example.proyectounidad2.Controller;

import java.io.ByteArrayInputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.proyectounidad2.Model.*;

import java.io.File;
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
    void subirArchivo(MouseEvent event) {
        File selectedFile= fileChooser.showOpenDialog(new Stage());
        if (selectedFile !=null){
            Image imagenSeleccionada = new Image(selectedFile.getPath());
            //Hacer algo con la imagen seleccionada
        }

    }

    FileChooser fileChooser=new FileChooser();
    Obra obra;
    boolean modo; //True->Añadir False->Modificar

    public void initialize() {
        cargarCmbs();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.setTitle("Cargar imagen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".jpg","*.jpg"),
                new FileChooser.ExtensionFilter(".png","*.png"),new FileChooser.ExtensionFilter("All images","*.jpg","*.png"));


        Platform.runLater(() -> {
            if (!modo) {
                lbl_titulodlg.setText("Modificar obra");
                cargarDatosObra();
            }{
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
        for (Autor autor : autores){
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

        System.out.println(dbConnector.getDepartamentoById(obra.getId_departamento()).toString());

        chk_popular.setSelected(obra.isPopular());
        ap_imagenHolder.setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(new ByteArrayInputStream(obra.getImg())),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
                        )
                )
        );
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

            //ESTO podemos hacerlo cuando se suba una imagen nueva
            // Recoger estilo o imagen desde el AnchorPane
            /*String imagenEstilo = ap_imagenHolder.getStyle();
            String imgPath = ""; // Extraer el path desde el estilo, si es necesario.
            if (imagenEstilo != null && imagenEstilo.contains("url(")) {
                int start = imagenEstilo.indexOf("url(") + 4;
                int end = imagenEstilo.indexOf(")", start);
                if (start > 0 && end > start) {
                    imgPath = imagenEstilo.substring(start, end).replace("\"", "");
                }
            }*/

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
        } catch (Exception e) {
            System.out.println("Error modificando la obra: " + e.getMessage());
        }


    }


}
