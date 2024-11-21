package org.example.proyectounidad2.Controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
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
    @FXML
    private AnchorPane ap_imgcontainer;

    public void initialize() {
        Platform.runLater(() -> {

            ap_imgcontainer.prefWidthProperty().bind(((Region) ap_imgcontainer.getParent()).widthProperty().multiply(0.5));
            String placeholder1 = "src/main/resources/imagenes/aedba790-5d99-4eec-9453-103efd6a1429_3000.jpg"; // Replace with your actual path
            String placeholder2 = "src/main/resources/imagenes/placeholder2.jpg"; // Replace with your actual path

            ap_imgcontainer.setStyle(
                    "-fx-background-image: url('file:" + placeholder2 + "');" +
                            "-fx-background-size: contain;" +
                            "-fx-background-repeat: no-repeat;" +
                            "-fx-background-position: right center;"
            );




            //img_imagenObra.setPreserveRatio(true);
            /*img_imagenObra.setPreserveRatio(true);

            double parentWidth = ((Region) img_imagenObra.getParent()).getWidth();
            double parentHeight = ((Region) img_imagenObra.getParent()).getHeight();

            double imgWidth = img_imagenObra.getFitWidth();
            double imgHeight = img_imagenObra.getFitHeight();

            System.out.println("Img width "+img_imagenObra.getFitWidth());
            System.out.println("Img height "+img_imagenObra.getFitHeight());

            if (imgHeight>imgWidth){
                img_imagenObra.setFitWidth(0);
                img_imagenObra.setFitHeight(parentHeight);
            }
            else {
                img_imagenObra.setFitWidth(parentWidth * 0.5);
                img_imagenObra.setFitHeight(0);
            }


            System.out.println("Parent width "+parentWidth);
            System.out.println("Img width "+img_imagenObra.getFitWidth());
            System.out.println("Parent height "+parentHeight);
            System.out.println("Img height "+img_imagenObra.getFitHeight());*/
        });
    }

}
