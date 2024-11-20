package org.example.proyectounidad2.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    void buscarAutor(MouseEvent event) {

    }

    @FXML
    void buscarObra(MouseEvent event) {

    }

    @FXML
    void mostrarAddObra(KeyEvent event) {

    }

}
