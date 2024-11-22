package org.example.proyectounidad2.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.proyectounidad2.Model.*;

import java.util.ArrayList;

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

    public void initialize(){

        cargarCmbs();

    }
    private void cargarCmbs(){

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
    public void setObra(Obra obra){
        this.obra=obra;

    }

    public void cargarDatosObra(){
        tf_titulo.setText(obra.getTitulo());
        tf_autor.setText(obra.getTitulo());
        tf_medidas.setText(obra.getMedidas());
        tf_medio.setText(obra.getMedio());
        tf_fecha.setText(obra.getFecha());

        cmb_categoria.setValue(obra.getCategoria());
        cmb_movimiento.setValue(dbConnector.getMovimientoById(obra.getId_movimiento()));
        cmb_departamento.setValue(dbConnector.getDepartamentoById(obra.getId_departamento()));

        chk_popular.setSelected(obra.isPopular());
        ap_imagenHolder.setStyle(
                "-fx-background-image: url(" + obra.getImg() + ");" +
                        "-fx-background-size: contain;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-position: right center;"
        );
    }

}
