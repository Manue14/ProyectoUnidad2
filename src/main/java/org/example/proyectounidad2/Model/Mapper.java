package org.example.proyectounidad2.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {
    public static Autor mapAutor(ResultSet rs) throws SQLException {
        return new Autor(rs.getInt("id"), rs.getString("nombre"),
                rs.getString("apellido1"), rs.getString("apellido2"),
                rs.getDate("nacimiento").toLocalDate(), rs.getDate("fallecimiento").toLocalDate(),
                rs.getString("nacionalidad"), rs.getString("foto"));
    }

    public static Departamento mapDepartamento(ResultSet rs) throws SQLException {
        return new Departamento(rs.getInt("id"), rs.getString("nombre"),
                rs.getString("descripcion"));
    }

    public static Movimiento mapMovimineto(ResultSet rs) throws SQLException {
        return new Movimiento(rs.getInt("id"), rs.getString("nombre"),
                rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate(),
                rs.getString("descripcion"));
    }

    public static Obra mapObra(ResultSet rs) throws SQLException {
        return new Obra(rs.getInt("id"), rs.getString("titulo"), rs.getFloat("alto"),
                rs.getFloat("ancho"), rs.getString("imagen"), rs.getBoolean("popular"),
                rs.getString("medio"),
                Categoria.getByValor(rs.getObject("categoria").toString()), rs.getString("fecha"),
                rs.getString("descripcion"), rs.getInt("id_autor"),
                rs.getInt("id_departamento"), rs.getInt("id_movimiento"));
    }
}
