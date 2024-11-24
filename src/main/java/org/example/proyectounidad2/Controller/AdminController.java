package org.example.proyectounidad2.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.ByteArrayInputStream;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.proyectounidad2.HelloApplication;
import org.example.proyectounidad2.Model.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;

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
    private ComboBox<Categoria> cmb_categoria;

    @FXML
    private ComboBox<Departamento> cmb_departamento;

    @FXML
    private ComboBox<Movimiento> cmb_movimiento;

    @FXML
    private ComboBox<String> cmb_nacionalidad;

    @FXML
    private CheckBox chk_popular;

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
    void mostrarAddAutor(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(getClass().getResource("/org/example/proyectounidad2/autorDialog.fxml"));
        } catch (Exception e) {
            System.out.println("Error setting location");
        }


        DialogPane autorDialogPane = fxmlLoader.load();
        //pasar la obra al dialog
        AutorDialogController autorDlgController = fxmlLoader.getController();
        try {
            if (autorDlgController == null) {
                System.out.println("No se encontro controlador");
            }
            autorDlgController.setModo(true);
        } catch (Exception e) {
            System.out.println("Error cargando modo del dlg");
        }

        //Mostrar dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(autorDialogPane);
        dialog.setTitle("Añadir Autor");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            autorDlgController.createAutor();
            cargarTablaAutores(dbConnector.getAllAutores());
        } else {
            AlertMaker.showInformation("Acion de añadir cancelada", "No se ha añadido ninguna obra");

        }
    }

    @FXML
    void mostrarAddObra(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(getClass().getResource("/org/example/proyectounidad2/obraDialog.fxml"));
        } catch (Exception e) {
            System.out.println("Error setting location");
        }


        DialogPane obraDialogPane = fxmlLoader.load();
        //pasar la obra al dialog
        ObraDialogController obraDlgController = fxmlLoader.getController();
        try {
            if (obraDlgController == null) {
                System.out.println("No se encontro controlador");
            }
            obraDlgController.setModo(true);
        } catch (Exception e) {
            System.out.println("Error cargando modo del dlg");
        }

        //Mostrar dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(obraDialogPane);
        dialog.setTitle("Añadir Obra");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            obraDlgController.createObra();
            cargarTablaObras(dbConnector.getAllObras());
        } else {
            AlertMaker.showInformation("Acion de añadir cancelada", "No se ha añadido ninguna obra");

        }
    }

    @FXML
    void action_eliminarAutor(ActionEvent event) {
        Autor selectedAutor = tbl_autores.getSelectionModel().getSelectedItem();
        System.out.println(selectedAutor);
        if (selectedAutor == null) {
            AlertMaker.showWarning("No autor seleccionado", "Por favor selecciona un autor que elminiar");
        } else {
            if (AlertMaker.showConfirmation("Eliminar Autor", "¿Seguro que desea eliminar este autor?")) {
                boolean resultado = dbConnector.deleteAutor(selectedAutor.getId());
                if (resultado) {
                    AlertMaker.showInformation("Eliminacion Exitosa", "Se ha eliminado el autor con exito");
                    cargarTablaAutores(dbConnector.getAllAutores());
                    cargarTablaObras(dbConnector.getAllObras());
                } else {
                    AlertMaker.showError("Error en la eliminacion", "Algo ha salido mal al borrar el autor");
                }
            } else {
                AlertMaker.showInformation("Eliminacion cancelada", "No se ha eliminado ningun autor");
            }
        }
    }

    @FXML
    void action_eliminarObra(ActionEvent event) {
        //GET selected item
        Obra selectedObra = tbl_obras.getSelectionModel().getSelectedItem();
        if (selectedObra == null) {
            AlertMaker.showWarning("No obra seleccionada", "Por favor selecciona una obra para elminiar primero");
        } else {
            if (AlertMaker.showConfirmation("Eliminar Obra", "¿Seguro que desea eliminar esta obra?")) {
                boolean resultado = dbConnector.deleteObra(selectedObra);
                if (resultado) {
                    AlertMaker.showInformation("Eliminacion Exitosa", "Se ha eliminado la obra con exito");
                    cargarTablaObras(dbConnector.getAllObras());
                    cargarTablaAutores(dbConnector.getAllAutores());
                } else {
                    AlertMaker.showError("Error en la eliminacion", "Algo ha salido mal al borrar la obra");
                }
            } else {
                AlertMaker.showInformation("Eliminacion cancelada", "No se ha eliminado ninguna obra");
            }
        }

    }

    @FXML
    void action_modificarAutor(ActionEvent event) throws IOException {
        Autor selectedAutor = tbl_autores.getSelectionModel().getSelectedItem();

        if (selectedAutor == null) {
            AlertMaker.showWarning("No autor seleccionado", "Por favor seleccione un autor para modificar");
            return;
        }
        //System.out.println("Autor deleccionado: " + selectedAutor.toString());

        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(getClass().getResource("/org/example/proyectounidad2/autorDialog.fxml"));
            System.out.println("Location: " + fxmlLoader.getLocation());
        } catch (Exception e) {
            System.out.println("Error setting location");
        }


        DialogPane autorDialogPane = fxmlLoader.load();
        //pasar la obra al dialog
        AutorDialogController autorDlgController = fxmlLoader.getController();
        try {
            if (autorDlgController == null) {
                System.out.println("No se encontro controlador");
            }
            autorDlgController.setAutor(selectedAutor);
            autorDlgController.setModo(false);
        } catch (Exception e) {
            System.out.println("Error poniendo autor");
        }

        //Mostrar dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(autorDialogPane);
        dialog.setTitle("Modificar Autor");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            autorDlgController.modificarAutor();
            cargarTablaAutores(dbConnector.getAllAutores());
            cargarTablaObras(dbConnector.getAllObras());
        } else {
            AlertMaker.showInformation("Modificacion cancelada", "No se ha modificado ningun autor");

        }
    }

    @FXML
    void action_modificarObra(ActionEvent event) throws IOException {
        Obra selectedObra = tbl_obras.getSelectionModel().getSelectedItem();
        if (selectedObra == null) {
            AlertMaker.showWarning("No obra seleccionada", "Por favor selecciona una obra para modificar primero");
            return;
        }

        //System.out.println("Obra seleccionada: " + selectedObra.toString());

        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(getClass().getResource("/org/example/proyectounidad2/obraDialog.fxml"));
        } catch (Exception e) {
            System.out.println("Error setting location");
        }


        DialogPane obraDialogPane = fxmlLoader.load();
        //pasar la obra al dialog
        ObraDialogController obraDlgController = fxmlLoader.getController();
        try {
            if (obraDlgController == null) {
                System.out.println("No se encontro controlador");
            }
            obraDlgController.setObra(selectedObra);
            obraDlgController.setModo(false);
        } catch (Exception e) {
            System.out.println("Error poniendo obra");
        }

        //Mostrar dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(obraDialogPane);
        dialog.setTitle("Modificar Obra");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK) {
            obraDlgController.modificarObra();
            cargarTablaObras(dbConnector.getAllObras());

            //RECOGER ESA OBRA Y PASARSELA A LA BASE DE DATOS
        } else {
            AlertMaker.showInformation("Modificacion cancelada", "No se ha modificado ninguna obra");

        }
    }

    public void initialize() {
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

    private void cargarTablaAutores(ArrayList<Autor> listaAutores) {
        tbl_autores.getItems().clear();


        col_idAutor.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_idAutor.setStyle("-fx-alignment: CENTER;");
        col_nombreAutor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellido1Autor.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        col_apellido2Autor.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        col_fotoAutor.setCellValueFactory(new PropertyValueFactory<>("foto"));
        col_muerteAutor.setCellValueFactory(new PropertyValueFactory<>("fallecimiento"));
        col_nacimientoAutor.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
        col_nacionalidadAutor.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));

        listaAutores.forEach(autor -> {
            tbl_autores.getItems().add(autor);

            /*Test cargar imagen*/
            /*if (autor.getFoto() != null) {
                img_test.setImage(new Image(new ByteArrayInputStream(autor.getFoto())));
            }*/

        });

    }

    public void cargarTablaObras(ArrayList<Obra> listaObras) {
        try {
            tbl_obras.getItems().clear();

            col_idObra.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_idObra.setStyle("-fx-alignment: CENTER;");
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
                Autor autor = dbConnector.getAutorById(obra.getId_autor());

                return new ReadOnlyStringWrapper(autor.getNombre() + " " + autor.getApellido1());
            });
            col_departamentoObra.setCellValueFactory(cellData -> {
                Obra obra = cellData.getValue();
                Departamento departamento = dbConnector.getDepartamentoById(obra.getId_departamento());

                return new ReadOnlyStringWrapper(departamento.getNombre());
            });
            col_movimientoObra.setCellValueFactory(cellData -> {
                Obra obra = cellData.getValue();
                Movimiento movimiento = dbConnector.getMovimientoById(obra.getId_movimiento());

                return new ReadOnlyStringWrapper(movimiento.getNombre());
            });

            listaObras.forEach(obra -> {
                tbl_obras.getItems().add(obra);
            });
        } catch (Exception e) {
            System.out.println("Error cargando datos obras: " + e);
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

        cmb_nacionalidad.getItems().clear();
        cmb_nacionalidad.getItems().add(null);
        ArrayList<String> nacionalidades = dbConnector.getAllNacionalidades();
        for (String nacionalidad : nacionalidades) {
            cmb_nacionalidad.getItems().add(nacionalidad);
        }
    }

    public void CerrarSesion(ActionEvent actionEvent) throws IOException {

        HelloApplication.setRoot("acceso");
    }

    public void CerrarPrograma(ActionEvent actionEvent) {
        Platform.exit();

    }

    public void ExportarObras(MouseEvent event) {

    }

    public void buscarAutor(ActionEvent actionEvent) {

        String nombre = tf_nombre.getText();
        String apellido1 = tf_apellido1.getText();
        String apellido2 = tf_apellido2.getText();
        String nacionalidad = cmb_nacionalidad.getValue(); // Asumiendo que la nacionalidad es un ComboBox

        // Crear un objeto con los campos a filtrar
        QueryFieldsObjectAutor fields = new QueryFieldsObjectAutor();
        fields.setNombre(nombre);
        fields.setApellido1(apellido1);
        fields.setApellido2(apellido2);
        fields.setNacionalidad(nacionalidad);

        // Llamar a la función de filtrado
        ArrayList<Autor> autoresFiltrados = dbConnector.filterAutores(fields);

        // Limpiar la tabla y cargar los resultados
        cargarTablaAutores(autoresFiltrados);


    }

    public void buscarObra(ActionEvent actionEvent) {
        QueryFieldsObjectObra fields = new QueryFieldsObjectObra();
        String titulo = tf_titulo.getText();
        String autor = tf_autor.getText();
        Departamento departamentoSeleccionado = cmb_departamento.getValue();
        Movimiento movimientoSeleccionado = cmb_movimiento.getValue();
        Categoria categoriaSeleccionada = cmb_categoria.getValue();
        
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

        // Llamar a la función de filtrado
        ArrayList<Obra> obrasFiltradas = dbConnector.filterObras(fields);

        // Limpiar la tabla y cargar los resultados
        cargarTablaObras(obrasFiltradas);
    }


    public int obtenerIdAutor(String nombreAutor) {
        return dbConnector.obtenerIdAutorPorNombre(nombreAutor);
    }


}
