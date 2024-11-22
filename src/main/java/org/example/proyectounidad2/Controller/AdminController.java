package org.example.proyectounidad2.Controller;

import java.io.ByteArrayInputStream;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.proyectounidad2.HelloApplication;
import org.example.proyectounidad2.Model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static org.example.proyectounidad2.HelloApplication.dbConnector;
import static org.example.proyectounidad2.HelloApplication.setRoot;

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
    private ComboBox<Categoria> cmb_categoria;

    @FXML
    private ComboBox<Departamento> cmb_departamento;

    @FXML
    private ComboBox<Movimiento> cmb_movimiento;

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
    private ImageView img_test;

    @FXML
    void buscarAutor(MouseEvent event) {

    }

    @FXML
    void buscarObra(MouseEvent event) {

    }

    @FXML
    void mostrarAddAutor(MouseEvent event) {

    }

    @FXML
    void mostrarAddObra(MouseEvent event) throws IOException {

        /*FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("obraDialog.fxml"));
        DialogPane obraDialogPane =fxmlLoader.load();*/


    }

    @FXML
    void action_eliminarAutor(ActionEvent event) {
        Autor selectedAutor = tbl_autores.getSelectionModel().getSelectedItem();
        if (selectedAutor == null){
            AlertMaker.showWarning("No autor seleccionado", "Por favor selecciona un autor que elminiar");
        }
        else{
            if (AlertMaker.showConfirmation("Eliminar Autor","¿Seguro que desea eliminar este autor?")){
                boolean resultado = dbConnector.deleteAutor(selectedAutor.getId());
                if(resultado){
                    AlertMaker.showInformation("Eliminacion Exitosa","Se ha eliminado el autor con exito");
                    cargarTablaAutores(dbConnector.getAllAutores());
                }
                else {
                    AlertMaker.showError("Error en la eliminacion","Algo ha salido mal al borrar el autor");
                }
            }
            else{
                AlertMaker.showInformation("Eliminacion cancelada","No se ha eliminado ningun autor");
            }
        }
    }

    @FXML
    void action_eliminarObra(ActionEvent event) {
        //GET selected item
        Obra selectedObra = tbl_obras.getSelectionModel().getSelectedItem();
        if (selectedObra == null){
            AlertMaker.showWarning("No obra seleccionada", "Por favor selecciona una obra para elminiar primero");
        }
        else{
            if (AlertMaker.showConfirmation("Eliminar Obra","¿Seguro que desea eliminar esta obra?")){
                boolean resultado = dbConnector.deleteObra(selectedObra);
                if(resultado){
                    AlertMaker.showInformation("Eliminacion Exitosa","Se ha eliminado la obra con exito");
                    cargarTablaObras(dbConnector.getAllObras());
                }
                else {
                    AlertMaker.showError("Error en la eliminacion","Algo ha salido mal al borrar la obra");
                }
            }
            else{
                AlertMaker.showInformation("Eliminacion cancelada","No se ha eliminado ninguna obra");
            }
        }

    }

    @FXML
    void action_modificarAutor(ActionEvent event) {

    }

    @FXML
    void action_modificarObra(ActionEvent event) throws IOException {
        Obra selectedObra = tbl_obras.getSelectionModel().getSelectedItem();
        System.out.println(selectedObra.toString());
        if (selectedObra == null){
            AlertMaker.showWarning("No obra seleccionada", "Por favor selecciona una obra para modificar primero");
        }

        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(getClass().getResource("/org/example/proyectounidad2/obraDialog.fxml"));
        } catch (Exception e) {
            System.out.println("Error setting location");
        }


        DialogPane obraDialogPane =fxmlLoader.load();
        //pasar la obra al dialog
        try {
            ObraDialogController obraDlgController = fxmlLoader.getController();
            if(obraDlgController==null){
                System.out.println("No se encontro controlador");
            }
            obraDlgController.setObra(selectedObra);
            obraDlgController.setModo(false);
        }catch (Exception e){
            System.out.println("Error poniendo obra");
        }

        //Mostrar dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(obraDialogPane);
        dialog.setTitle("Modificar Obra");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK){
            //ACCION MODIFICAR
        }else {
            AlertMaker.showInformation("Modificacion cancelada","No se ha modificado ninguna obra");

        }


    }

    public void initialize(){
        //Poner las columnas para que ocupen toda la tabla
        setupColumnWidths(tbl_autores);
        setupColumnWidths(tbl_obras);

        //Cargar datos en las columnas
        ArrayList<Obra> listaObras = dbConnector.getAllObras();
        ArrayList<Autor> listaAutores = dbConnector.getAllAutores();

        cargarTablaAutores(listaAutores);
        cargarTablaObras(listaObras);
        cargarCmbs();

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

    private void cargarTablaAutores(ArrayList<Autor> listaAutores){

        col_idAutor.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nombreAutor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellido1Autor.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        col_apellido2Autor.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        col_fotoAutor.setCellValueFactory(new PropertyValueFactory<>("foto"));
        col_muerteAutor.setCellValueFactory(new PropertyValueFactory<>("fallecimiento"));
        col_nacimientoAutor.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
        col_nacionalidadAutor.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));

        listaAutores.forEach(autor->{
            tbl_autores.getItems().add(autor);
            
            /*Test cargar imagen*/
            /*if (autor.getFoto() != null) {
                img_test.setImage(new Image(new ByteArrayInputStream(autor.getFoto())));
            }*/
            
        });

    }
    public void cargarTablaObras(ArrayList<Obra> listaObras){
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
                tbl_obras.getItems().add(obra);
            });
        }catch (Exception e){
            System.out.println("Error cargando datos obras: "+e);
        }
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

        cmb_nacionalidad.getItems().clear();
        cmb_nacionalidad.getItems().add(null);
        ArrayList<String> nacionalidades = dbConnector.getAllNacionalidades();
        for (String nacionalidad : nacionalidades) {
            cmb_nacionalidad.getItems().add(nacionalidad);
        }
    }

}
