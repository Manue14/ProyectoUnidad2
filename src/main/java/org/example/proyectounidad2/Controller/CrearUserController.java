package org.example.proyectounidad2.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.proyectounidad2.HelloApplication;
import org.example.proyectounidad2.Model.UserModel;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrearUserController {


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordFieldRepeticion;

    @FXML
    private Label messageLabel;


    public void CrearUsuario() throws IOException {
        // Limpiar cualquier error anterior
        clearErrors();

        boolean hasError = false;
        String username = usernameField.getText();
        String contraseña = passwordField.getText();
        String contreseñaRepetida = passwordFieldRepeticion.getText();

        // Verificar si el campo de nombre de usuario está vacío
        if (username.isEmpty()) {
            usernameField.getStyleClass().add("error-border");
            usernameField.getStyleClass().add("error-placeholder");
            messageLabel.setText("Por favor, ingresa un nombre de usuario.");
            hasError = true;
        }

        // Verificar si el campo de contraseña está vacío
        if (contraseña.isEmpty()) {
            passwordField.getStyleClass().add("error-border");
            passwordField.getStyleClass().add("error-placeholder");
            messageLabel.setText("Por favor, ingresa una contraseña.");
            hasError = true;
        }

        // Verificar si el campo de repetición de contraseña está vacío
        if (contreseñaRepetida.isEmpty()) {
            passwordFieldRepeticion.getStyleClass().add("error-border");
            passwordFieldRepeticion.getStyleClass().add("error-placeholder");
            messageLabel.setText("Por favor, repite la contraseña.");
            hasError = true;
        }

        // Si no hay error, proceder con el siguiente paso
        if (!hasError) {
            messageLabel.setText("Formulario correcto.");

            // Aquí creas el usuario con los datos validados
            UserModel model = new UserModel();
            String contraseñaHasheada = hashPassword(contraseña);
            model.CrearUsuario(username, contraseñaHasheada); // Pasar username y password al modelo
            Volver();
        }


    }

    // Limpiar los estilos de error antes de realizar una nueva validación
    private void clearErrors() {
        usernameField.getStyleClass().remove("error-border");
        passwordField.getStyleClass().remove("error-border");
        passwordFieldRepeticion.getStyleClass().remove("error-border");

        messageLabel.setText(""); // Limpiar mensaje de error
    }

    //Funcion repetida de AccesoControler, ver como usarla sin repetier en un futuro
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hashear la contraseña
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // Agregar un cero si es necesario
                }
                hexString.append(hex);
            }

            // Retornar el hash como cadena hexadecimal
            return hexString.toString();


        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public void Volver() throws IOException {

        HelloApplication.setRoot("acceso");

    }


}
