package org.example.proyectounidad2.Model;

import java.sql.*;

public class UserModel {
    private Connection connection;
    public static int permisosUser;

    public UserModel() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuariosMuseo", "manu", "abc123.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int validarCredenciales(String username, String hashedPassword) {
        if (connection == null) {
            return -1;
        }

        String query = "SELECT contraseña, idPermiso FROM users WHERE nombre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String storedPasswordHash = resultSet.getString("contraseña");

                    if (hashedPassword.equals(storedPasswordHash)) {
                        permisosUser = resultSet.getInt("idPermiso");

                        return 0; // 4 para admin, 3 para usuario base
                    } else {
                        return 2; // Contraseña incorrecta
                    }
                } else {
                    return 1; // Usuario no encontrado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Error en la conexión o consulta
        }
    }

    public void CrearUsuario(String nombre, String contraseñaHasheada) {
        if (connection == null) {
            System.out.println("Error: no se pudo establecer conexión a la base de datos.");
            return;
        }

        try {
            String sql = "INSERT INTO users (nombre, contraseña) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, contraseñaHasheada);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Usuario creado correctamente.");
                } else {
                    System.out.println("Error al crear el usuario.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos al intentar crear el usuario.");
        }
    }



}
