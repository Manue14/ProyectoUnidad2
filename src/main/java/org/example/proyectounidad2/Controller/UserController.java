package org.example.proyectounidad2.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.proyectounidad2.HelloApplication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.cell.PropertyValueFactory;
import static org.example.proyectounidad2.HelloApplication.dbConnector;
import org.example.proyectounidad2.Model.Autor;
import org.example.proyectounidad2.Model.Categoria;
import org.example.proyectounidad2.Model.Departamento;
import org.example.proyectounidad2.Model.Movimiento;
import org.example.proyectounidad2.Model.Obra;
import org.example.proyectounidad2.Model.QueryFieldsObjectObra;

public class UserController {

    Obra obrafiltrada;

    @FXML
    private Button btn_buscarObra;

    @FXML
    private CheckBox chk_popular;

    @FXML
    private ComboBox<Categoria> cmb_categoria;

    @FXML
    private ComboBox<Departamento> cmb_departamento;

    @FXML
    private ComboBox<Movimiento> cmb_movimiento;

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
    private HBox containerDatos;


    @FXML
    public void buscarObra(MouseEvent event) {
        QueryFieldsObjectObra fields = new QueryFieldsObjectObra();
        String titulo = tf_titulo.getText();
        String autor = tf_autor.getText();
        Departamento departamentoSeleccionado = cmb_departamento.getValue();
        Movimiento movimientoSeleccionado = cmb_movimiento.getValue();
        Categoria categoriaSeleccionada = cmb_categoria.getValue();
        System.out.println(titulo);
        if (titulo != null && !titulo.isEmpty()) {
            fields.setTitulo(titulo);
        }
        
        if (autor != null && !autor.isEmpty()) {
            fields.setAutor_nombre(autor);
        }
        
        if (departamentoSeleccionado != null) {
            fields.setDepartamento_id(departamentoSeleccionado.getId());
        }

        if (movimientoSeleccionado != null) {
            fields.setMovimiento_id(movimientoSeleccionado.getId());
        }

        if (categoriaSeleccionada != null) {
            fields.setCategoria(categoriaSeleccionada.getValor());
        }
        
        fields.setPopular(chk_popular.isSelected());
        System.out.println(fields);
        // Llamar a la función de filtrado
        ArrayList<Obra> obrasFiltradas = dbConnector.filterObras(fields);

        System.out.println(obrasFiltradas);

        cargarObrasFiltradas(obrasFiltradas);
        // Limpiar la tabla y cargar los resultados
        /*cargarTablaObras(obrasFiltradas);*/
    }

    @FXML
    private void cargarObrasFiltradas(ArrayList<Obra> obrasFiltradas) {
        // Asegurarte de que el contenedor sea visible
        containerDatos.setVisible(!obrasFiltradas.isEmpty());

        if (!obrasFiltradas.isEmpty()) {
            // Cargar la primera obra como ejemplo
            obrafiltrada = obrasFiltradas.get(0);
            mostrarObra(obrafiltrada);
        } else {
            AlertMaker.showInformation("Búsqueda", "No se encontraron obras con los filtros aplicados.");
        }
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
    @FXML
    private void mostrarObra(Obra obra) {
        Autor autor = dbConnector.getAutorById(obra.getId_autor());
        Departamento departamento = dbConnector.getDepartamentoById(obra.getId_departamento());

        // Actualizar los labels con la información de la obra
        lbl_tituloObra.setText(obra.getTitulo());
        lbl_autorObra.setText(autor != null ? autor.getNombre() : "Autor no encontrado");
        lbl_fechaObra.setText(obra.getFecha());
        lbl_departamentoObra.setText(departamento != null ? departamento.getNombre() : "Departamento no encontrado");
        lbl_categoriaObra.setText(obra.getCategoria().getValor());
        lbl_medioObra.setText(obra.getMedio());
        lbl_dimensionesObra.setText(obra.getAlto() + "x" + obra.getAncho());
        txt_descrippcionObra.setText(obra.getDescripcion());

        // Mostrar imagen si está disponible
        if (obra.getImg() != null) {
            String mimeType = "image/png";

            // Convert to Base64 and use it in a Data URL
            String base64Image = Base64.getEncoder().encodeToString(obra.getImg());
            String dataUrl = "data:" + mimeType + ";base64," + base64Image;
            
            //<div style="background:url( data:image/jpeg;base64,@Convert.ToBase64String(electedOfficial.Picture) )"></div>
            ap_imgcontainer.setStyle(
                    "-fx-background-image: url('" + dataUrl + "');"+
                    "-fx-background-size: contain;" +
                            "-fx-background-repeat: no-repeat;" +
                            "-fx-background-position: right center;"
            );
            /*ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            ap_imgcontainer.getChildren().clear();
            ap_imgcontainer.getChildren().add(imageView);*/
        } else {
            ap_imgcontainer.getChildren().clear();
            Label noImage = new Label("Sin imagen");
            ap_imgcontainer.getChildren().add(noImage);
        }
    }



    @FXML
    void mostrarAutor(MouseEvent event) {

    }
    @FXML
    private AnchorPane ap_imgcontainer;

    FileChooser fileChooser= new FileChooser();

    public void initialize() {
        cargarCmbs();
        Platform.runLater(() -> {

            ap_imgcontainer.prefWidthProperty().bind(((Region) ap_imgcontainer.getParent()).widthProperty().multiply(0.5));
            //ESTO es para comprobar que el anchor pane funciona bien
            /*String placeholder1 = "src/main/resources/imagenes/aedba790-5d99-4eec-9453-103efd6a1429_3000.jpg"; // Replace with your actual path
            String placeholder2 = "src/main/resources/imagenes/placeholder2.jpg"; // Replace with your actual path

            ap_imgcontainer.setStyle(
                    "-fx-background-image: url('file:" + placeholder2 + "');" +
                            "-fx-background-size: contain;" +
                            "-fx-background-repeat: no-repeat;" +
                 */

        });
    }

    public void CerrarSesion(ActionEvent actionEvent) throws IOException {

        HelloApplication.setRoot("acceso");
    }

    public void CerrarPrograma(ActionEvent actionEvent) {
        Platform.exit();

    }

    @FXML
    public void ExportarJson(ActionEvent actionEvent) {
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".json", "*.json")
        );

        fileChooser.setInitialFileName("datos_obras");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                // Escribir el JSON con las obras filtradas de la tabla
                mapper.writeValue(file, obrafiltrada);
                System.out.println("Archivo JSON escrito con éxito");
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
