package org.example.proyectounidad2.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.proyectounidad2.HelloApplication;
import org.example.proyectounidad2.Model.UserModel;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label PruebaPermiso;



    public void initialize() {
        int permiso = UserModel.permisosUser;

        // Mostrar el rol del usuario basado en el permiso
        if (permiso == 1) {
            PruebaPermiso.setText("Rol: Administrador");
        } else if (permiso == 2) {
            PruebaPermiso.setText("Rol: Usuario Base");
        }}

    @FXML
    private void CerrarSesion() throws IOException {

        HelloApplication.setRoot("acceso");
    }


}
