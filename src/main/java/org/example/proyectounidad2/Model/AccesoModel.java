package org.example.proyectounidad2.Model;

import java.sql.*;

public class AccesoModel {




    public int validarCredenciales(String username, String hashedPassword) {
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
                        if (hashedPassword.equals(storedPasswordHash)) {
                            return 0; // Credenciales válidas
                        } else {
                            return 2; // Contraseña incorrecta
                        }
                    } else {
                        return 1; // Usuario no encontrado
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Error en la conexión
        }
    }
}
