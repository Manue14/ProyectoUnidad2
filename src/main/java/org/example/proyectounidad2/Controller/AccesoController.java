package org.example.proyectounidad2.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.proyectounidad2.Model.AccesoModel;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccesoController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    public void validarCredenciales() throws IOException {

        //valores que metió el usuario
        String usernameAcceso = usernameField.getText();
        String passwordAcceso = passwordField.getText();
        String passwordaAccesoHaseada = hashPassword(passwordAcceso);

        AccesoModel model = new AccesoModel();


        boolean esValido = model.validarCredenciales(usernameAcceso, passwordaAccesoHaseada);

        // Mostrar el mensaje de acuerdo al resultado
        if (esValido) {
            messageLabel.setText("Acceso exitoso");
        } else {
            messageLabel.setText("Usuario o contraseña incorrectos");
        }

}


public static String hashPassword(String password) {
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





}