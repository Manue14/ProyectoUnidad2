import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class adminController {

    @FXML
    private Button btn_addAutor;

    @FXML
    private Button btn_addObra;

    @FXML
    private Button btn_buscarAutor;

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
    private ComboBox<?> cmb_nacionalidad;

    @FXML
    private TableView<?> tbl_autores;

    @FXML
    private TableView<?> tbl_obras;

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
    void mostrarAddAutor(MouseEvent event) {

    }

    @FXML
    void mostrarAddObra(MouseEvent event) {

    }

}
