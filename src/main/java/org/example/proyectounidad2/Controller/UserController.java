package org.example.proyectounidad2.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class UserController {

    @FXML
    private Button btn_buscarObra;

    @FXML
    private CheckBox chk_popular;

    @FXML
    private ComboBox<?> cmb_categoria;

    @FXML
    private ComboBox<?> cmb_departamento;

    @FXML
    private ComboBox<?> cmb_movimiento;

    @FXML
    private ImageView img_imagenObra;

    @FXML
    private Label lbl_autorObra;

    @FXML
    private Label lbl_categoriaObra;

    @FXML
    private Label lbl_departamentoObra;

    @FXML
    private Label lbl_dimensionesObra;

    @FXML
    private Label lbl_fechaObra;

    @FXML
    private Label lbl_medioObra;

    @FXML
    private Label lbl_tituloObra;

    @FXML
    private TextField tf_autor;

    @FXML
    private TextField tf_titulo;

    @FXML
    private Text txt_descrippcionObra;

    @FXML
    void buscarObra(MouseEvent event) {

    }

    @FXML
    void mostrarAutor(MouseEvent event) {

    }

}
