package org.example.proyectounidad2.Controller;

import java.io.ByteArrayInputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.proyectounidad2.Model.*;

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
    private Label lbl_titulodlg;

    @FXML
    private TextField tf_autor;

    @FXML
    private TextField tf_fecha;

    @FXML
    private TextField tf_medidas;

    @FXML
    private TextField tf_medio;

    @FXML
    private TextField tf_titulo;

    Obra obra;
    boolean modo; //True->Añadir False->Modificar

    public void initialize() {
        cargarCmbs();

        Platform.runLater(() -> {
            if (!modo) {
                cargarDatosObra();
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
    }


    public void cargarDatosObra() {
        tf_titulo.setText(obra.getTitulo());
        tf_autor.setText(obra.getTitulo()); //hacer combobox con nombre autores
        tf_medidas.setText(obra.getMedidas());
        tf_medio.setText(obra.getMedio());
        tf_fecha.setText(obra.getFecha());

        cmb_categoria.setValue(obra.getCategoria());
        cmb_movimiento.setValue(dbConnector.getMovimientoById(obra.getId_movimiento()));
        cmb_departamento.setValue(dbConnector.getDepartamentoById(obra.getId_departamento()));
        
        System.out.println(dbConnector.getMovimientoById(obra.getId_movimiento()));

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
            System.out.println("----------------------");
            String titulo = tf_autor.getText();
            String autor = tf_autor.getText();
            String medidas = tf_medidas.getText();
            String medio = tf_medio.getText();
            String fecha = tf_fecha.getText();

            // Recoger datos de los ComboBoxes
            Categoria categoria = (Categoria) cmb_categoria.getValue(); // Obtiene la selección como enum
            Movimiento movimiento = (Movimiento) cmb_movimiento.getValue();
            Departamento departamento = (Departamento) cmb_departamento.getValue();

            // Recoger estado del CheckBox
            boolean popular = chk_popular.isSelected();

            // Recoger estilo o imagen desde el AnchorPane
            String imagenEstilo = ap_imagenHolder.getStyle();
            String imgPath = ""; // Extraer el path desde el estilo, si es necesario.
            if (imagenEstilo != null && imagenEstilo.contains("url(")) {
                int start = imagenEstilo.indexOf("url(") + 4;
                int end = imagenEstilo.indexOf(")", start);
                if (start > 0 && end > start) {
                    imgPath = imagenEstilo.substring(start, end).replace("\"", "");
                }
            }

            // Imprimir datos recogidos para verificar
            System.out.println("Título: " + titulo);
            System.out.println("Autor: " + autor);
            System.out.println("Medidas: " + medidas);
            System.out.println("Medio: " + medio);
            System.out.println("Fecha: " + fecha);
            System.out.println("Categoría: " + categoria);
            System.out.println("Movimiento: " + (movimiento != null ? movimiento.getNombre() : "N/A"));
            System.out.println("Departamento: " + (departamento != null ? departamento.getNombre() : "N/A"));
            System.out.println("Popular: " + popular);
            System.out.println("Imagen URL: " + imgPath);

            // Aquí puedes usar los datos para actualizar la obra en la base de datos o realizar otras operaciones.
        } catch (Exception e) {
            System.out.println("Error modificando la obra: " + e.getMessage());
        }


    }


}
