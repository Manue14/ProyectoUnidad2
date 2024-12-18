package org.example.proyectounidad2.Model;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;

public class Mapper {
    public static Autor mapAutor(ResultSet rs) throws SQLException {
        LocalDate nacimiento = rs.getDate("nacimiento")==null?null:rs.getDate("nacimiento").toLocalDate();
        LocalDate fallecimiento = rs.getDate("fallecimiento")==null?null:rs.getDate("fallecimiento").toLocalDate();
        byte[] foto = rs.getBlob("foto")==null?null:rs.getBlob("foto").getBytes(1, (int) rs.getBlob("foto").length());

        Autor autor=new Autor(rs.getInt("id"), rs.getString("nombre"),
                rs.getString("apellido1"), rs.getString("apellido2"),
                nacimiento, fallecimiento,
                rs.getString("nacionalidad"), foto);

        return autor;
    }

    public static Departamento mapDepartamento(ResultSet rs) throws SQLException {
        return new Departamento(rs.getInt("id"), rs.getString("nombre"),
                rs.getString("descripcion"));
    }

    public static Movimiento mapMovimiento(ResultSet rs) throws SQLException {
        return new Movimiento(rs.getInt("id"), rs.getString("nombre"),
                rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate(),
                rs.getString("descripcion"));
    }

    public static Obra mapObra(ResultSet rs) throws SQLException {
        byte[] foto = rs.getBlob("imagen")==null?null:rs.getBlob("imagen").getBytes(1, (int) rs.getBlob("imagen").length());
        return new Obra(rs.getInt("id"), rs.getString("titulo"), rs.getFloat("alto"),
                rs.getFloat("ancho"), foto, rs.getBoolean("popular"),
                rs.getString("medio"),
                Categoria.getByValor(rs.getObject("categoria").toString()), rs.getString("fecha"),
                rs.getString("descripcion"), rs.getInt("id_autor"),
                rs.getInt("id_departamento"), rs.getInt("id_movimiento"));
    }
    
    public static void bindAutorUpdateQuery(PreparedStatement ps, Autor autor) throws SQLException {
        ps.setString(1, autor.getNombre());
        ps.setString(2, autor.getApellido1());
        ps.setString(3, autor.getApellido2());
        ps.setDate(4, Date.valueOf(autor.getNacimiento().toString()));
        if (autor.getFallecimiento() != null) {
            ps.setDate(5, Date.valueOf(autor.getFallecimiento().toString()));
        } else {
            ps.setDate(5, null);
        }
        ps.setString(6, autor.getNacionalidad());
        ps.setBytes(7, autor.getFoto());
        ps.setInt(8, autor.getId());
    }

    public static void bindAutorCreateQuery(PreparedStatement ps, Autor autor) throws SQLException {
        ps.setString(1, autor.getNombre());
        ps.setString(2, autor.getApellido1());
        ps.setString(3, autor.getApellido2());
        ps.setDate(4, Date.valueOf(autor.getNacimiento().toString()));
        if (autor.getFallecimiento() != null) {
            ps.setDate(5, Date.valueOf(autor.getFallecimiento().toString()));
        } else {
            ps.setDate(5, null);
        }
        
        ps.setString(6, autor.getNacionalidad());
        ps.setBytes(7, autor.getFoto());
    }
    
    public static void bindObraUpdateQuery(PreparedStatement ps, Obra obra) throws SQLException {
        ps.setString(1, obra.getTitulo());
        ps.setFloat(2, obra.getAlto());
        ps.setFloat(3, obra.getAncho());
        ps.setBytes(4, obra.getImg());
        ps.setBoolean(5, obra.isPopular());
        ps.setString(6, obra.getMedio());
        ps.setString(7, obra.getCategoria().getValor());
        ps.setString(8, obra.getFecha());
        ps.setString(9, obra.getDescripcion());
        ps.setInt(10, obra.getId_autor());
        ps.setInt(11, obra.getId_departamento());
        ps.setInt(12, obra.getId_movimiento());
        ps.setInt(13, obra.getId());
    }

    public static void bindObraCreateQuery(PreparedStatement ps, Obra obra) throws SQLException {
        ps.setString(1, obra.getTitulo());
        ps.setFloat(2, obra.getAlto());
        ps.setFloat(3, obra.getAncho());
        ps.setBytes(4, obra.getImg());
        ps.setBoolean(5, obra.isPopular());
        ps.setString(6, obra.getMedio());
        ps.setObject(7, obra.getCategoria().getValor());
        ps.setString(8, obra.getFecha());
        ps.setString(9, obra.getDescripcion());
        ps.setInt(10, obra.getId_autor());
        ps.setInt(11, obra.getId_departamento());
        ps.setInt(12, obra.getId_movimiento());
    }
}
