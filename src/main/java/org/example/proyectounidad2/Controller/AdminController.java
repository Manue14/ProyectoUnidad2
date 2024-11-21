package org.example.proyectounidad2.Controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.proyectounidad2.Model.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.example.proyectounidad2.HelloApplication.dbConnector;

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
    private ComboBox<String> cmb_categoria;

    @FXML
    private ComboBox<String> cmb_departamento;

    @FXML
    private ComboBox<String> cmb_movimiento;

    @FXML
    private ComboBox<String> cmb_nacionalidad;

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
    private TableView<Autor> tbl_autores;

    @FXML
    private TableView<Obra> tbl_obras;

    @FXML
    private TableColumn<Autor, String> col_apellido1Autor;

    @FXML
    private TableColumn<Autor, String> col_apellido2Autor;

    @FXML
    private TableColumn<Autor, byte[]> col_fotoAutor;

    @FXML
    private TableColumn<Autor, Integer> col_idAutor;

    @FXML
    private TableColumn<Autor, String> col_muerteAutor;

    @FXML
    private TableColumn<Autor, LocalDate> col_nacimientoAutor;

    @FXML
    private TableColumn<Autor, LocalDate> col_nacionalidadAutor;

    @FXML
    private TableColumn<Autor, String> col_nombreAutor;

    @FXML
    private TableColumn<Obra, String> col_autorObra;

    @FXML
    private TableColumn<Obra, Categoria> col_categoriaObra;

    @FXML
    private TableColumn<Obra, String> col_departamentoObra;

    @FXML
    private TableColumn<Obra, String> col_fechaObra;

    @FXML
    private TableColumn<Obra, Integer> col_idObra;

    @FXML
    private TableColumn<Obra, String> col_medidasObra;

    @FXML
    private TableColumn<Obra, String> col_medioObra;

    @FXML
    private TableColumn<Obra, String> col_movimientoObra;

    @FXML
    private TableColumn<Obra, Boolean> col_popularObra;

    @FXML
    private TableColumn<Obra, String> col_tituloObra;

    @FXML
    void buscarAutor(MouseEvent event) {

    }

    @FXML
    void buscarObra(MouseEvent event) {

    }

    @FXML
    void mostrarAddObra(KeyEvent event) {

    }

    @FXML
    void action_eliminarAutor(ActionEvent event) {

    }

    @FXML
    void action_eliminarObra(ActionEvent event) {

    }

    @FXML
    void action_modificarAutor(ActionEvent event) {

    }

    @FXML
    void action_modificarObra(ActionEvent event) {

    }

    public void initialize(){
        //Poner las columnas para que ocupen toda la tabla
        setupColumnWidths(tbl_autores);
        setupColumnWidths(tbl_obras);

        //Cargar datos en las columnas
        ArrayList<Object> listaObras = dbConnector.getAllFromTable(Table.valueOf("OBRAS"));
        ArrayList<Object> listaAutores = dbConnector.getAllFromTable(Table.valueOf("AUTORES"));

        cargarTablaAutores(listaAutores);
        cargarTablaObras(listaObras);

        //Cargar combobox
        Platform.runLater(() -> {
            cargarCmbs();
        });

        //System.out.println(listaObras.toString());
        //System.out.println(listaAutores.toString());
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

    private void cargarTablaAutores(ArrayList<Object> listaAutores){

        col_idAutor.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nombreAutor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellido1Autor.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        col_apellido2Autor.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        col_fotoAutor.setCellValueFactory(new PropertyValueFactory<>("foto"));
        col_muerteAutor.setCellValueFactory(new PropertyValueFactory<>("fallecimiento"));
        col_nacimientoAutor.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
        col_nacionalidadAutor.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));

        listaAutores.forEach(autor->{
            tbl_autores.getItems().add((Autor)autor);
        });

    }
    public void cargarTablaObras(ArrayList<Object> listaObras){
        try {
            col_idObra.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_tituloObra.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            col_medidasObra.setCellValueFactory(cellData -> {
                Obra obra = cellData.getValue();
                return new ReadOnlyStringWrapper(obra.getAlto() + " x " + obra.getAncho());
            });
            col_popularObra.setCellValueFactory(new PropertyValueFactory<>("popular"));
            col_medioObra.setCellValueFactory(new PropertyValueFactory<>("medio"));
            col_categoriaObra.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            col_fechaObra.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            col_autorObra.setCellValueFactory(cellData -> {
                Obra obra = cellData.getValue();
                Autor autor= dbConnector.getAutorById(obra.getId_autor());

                return new ReadOnlyStringWrapper(autor.getNombre() + " " + autor.getApellido1());
            });
            col_departamentoObra.setCellValueFactory(cellData -> {
                Obra obra = cellData.getValue();
                Departamento departamento= dbConnector.getDepartamentoById(obra.getId_departamento());

                return new ReadOnlyStringWrapper(departamento.getNombre());
            });
            col_movimientoObra.setCellValueFactory(cellData -> {
                Obra obra = cellData.getValue();
                Movimiento movimiento= dbConnector.getMovimientoById(obra.getId_movimiento());

                return new ReadOnlyStringWrapper(movimiento.getNombre());
            });

            listaObras.forEach(obra->{
                tbl_obras.getItems().add((Obra)obra);
            });
        }catch (Exception e){
            System.out.println("Error cargando datos obras: "+e);
        }
    }
    private void cargarCmbs(){
        ArrayList<Object> departamentos = dbConnector.getAllFromTable(Table.DEPARTAMENTOS);
        ArrayList<Object> movimientos = dbConnector.getAllFromTable(Table.MOVIMIENTOS);
        ArrayList<String> nacionalidades = dbConnector.getNacionalidades();//inventa metodo para conseguir nacionalidades
        departamentos.forEach(departamento -> {
            cmb_departamento.getItems().add(((Departamento)departamento).getNombre());

        });
        movimientos.forEach(movimiento ->{
            cmb_movimiento.getItems().add(((Movimiento)movimiento).getNombre());
        });
        for (Categoria categoria :Categoria.values() ){
            cmb_categoria.getItems().add(categoria.getValor());
        }
        nacionalidades.forEach(nacionalidad->{
            cmb_nacionalidad.getItems().add(nacionalidad);
        });

        //cmb_nacionalidad.getItems().add();

    }

}
