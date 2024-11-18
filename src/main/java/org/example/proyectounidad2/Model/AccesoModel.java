package org.example.proyectounidad2.Model;

import java.sql.*;

public class AccesoModel {




    public boolean validarCredenciales(String username, String hashedPassword) {
        // Conexión a la base de datos
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuariosMuseo", "root", "root")) {

            // Consulta para verificar si el usuario existe y obtener su contraseña
            String query = "SELECT contraseña FROM users WHERE nombre = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (resultSet.next()) {
                        // Obtener la contraseña almacenada en la base de datos
                        String storedPasswordHash = resultSet.getString("contraseña");

                        // Comparar la contraseña proporcionada con la almacenada (hasheada)
                        return hashedPassword.equals(storedPasswordHash);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Si no se encuentra el usuario o ocurre algún error, devuelve false
    }
}
