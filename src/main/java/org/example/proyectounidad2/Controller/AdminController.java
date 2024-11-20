package org.example.proyectounidad2.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AdminController {

    @FXML
    private Button btn_addAutores;

    @FXML
    private Button btn_addObras;

    @FXML
    private Button btn_buscarAutor;

    @FXML
    private Button btn_buscarObra;

    @FXML
    private ComboBox<?> cmb_categoria;

    @FXML
    private ComboBox<?> cmb_departamento;

    @FXML
    private ComboBox<?> cmb_movimiento;

    @FXML
    private ComboBox<?> cmb_nacionalidad;

    @FXML
    private TextField tf_apellido1;

    @FXML
    private TextField tf_apellido2;

    @FXML
    private TextField tf_autor;

    @FXML
    private TextField tf_nombre;

    @FXML
    private TextField tf_titulo;

    @FXML
    private TableView<?> tbl_autores;

    @FXML
    private TableView<?> tbl_obras;

    @FXML
    void buscarAutor(MouseEvent event) {

    }

    @FXML
    void buscarObra(MouseEvent event) {

    }

    @FXML
    void mostrarAddObra(KeyEvent event) {

    }

    public void initialize(){
        setupColumnWidths(tbl_autores);
        setupColumnWidths(tbl_obras);
    }

    private void setupColumnWidths(TableView<?> tableView) {
        // Add a listener to ensure the columns resize when the TableView size changes
        tableView.widthProperty().addListener((obs, oldWidth, newWidth) -> adjustColumnWidths(tableView));

        // Initial adjustment
        adjustColumnWidths(tableView);
    }

    private void adjustColumnWidths(TableView<?> tableView) {
        int columnCount = tableView.getColumns().size();
        if (columnCount > 0) {
            double totalWidth = tableView.getWidth();
            double columnWidth = totalWidth / columnCount;

            // Set the width for each column
            for (TableColumn<?, ?> column : tableView.getColumns()) {
                column.setPrefWidth(columnWidth);
            }
        }
    }

}
