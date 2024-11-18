package org.example.proyectounidad2.Model;

import java.sql.*;

public class DBConnector {
    private String url = "jdbc:mysql://localhost:3306/Coleccion";
    private Connection conn;

    public DBConnector() throws SQLException{
        this.conn = DriverManager.getConnection(url, "root", "root");
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public Autor getAutorById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("select * from Autor where id = ?")) {
            if (checkIfIdExists(id, Table.AUTORES) == 0) {
                throw new SQLException("El autor con id " + id + " no existe");
            } else if (checkIfIdExists(id, Table.AUTORES) == -1) {
                throw new SQLException("Error al conectar con la base de datos");
            }

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Autor autor = Mapper.mapAutor(rs);
            rs.close();

            return autor;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public Departamento getDepartamentoById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("select * from Departamento where id = ?")) {
            if (checkIfIdExists(id, Table.DEPARTAMENTOS) == 0) {
                throw new SQLException("El departamento con id " + id + " no existe");
            } else if (checkIfIdExists(id, Table.DEPARTAMENTOS) == -1) {
                throw new SQLException("Error al conectar con la base de datos");
            }

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Departamento departamento = Mapper.mapDepartamento(rs);
            rs.close();

            return departamento;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public Movimiento getMovimientoById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("select * from Movimiento where id = ?")) {
            if (checkIfIdExists(id, Table.MOVIMIENTOS) == 0) {
                throw new SQLException("El movimiento con id " + id + " no existe");
            } else if (checkIfIdExists(id, Table.MOVIMIENTOS) == -1) {
                throw new SQLException("Error al conectar con la base de datos");
            }

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Movimiento movimiento = Mapper.mapMovimineto(rs);
            rs.close();

            return movimiento;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public Obra getObraById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("select * from Obra where id = ?")) {
            if (checkIfIdExists(id, Table.OBRAS) == 0) {
                throw new SQLException("La obra con id " + id + " no existe");
            } else if (checkIfIdExists(id, Table.OBRAS) == -1) {
                throw new SQLException("Error al conectar con la base de datos");
            }

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Obra obra = Mapper.mapObra(rs);
            rs.close();

            return obra;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public int checkIfIdExists(int id, Table table) {
        try (PreparedStatement ps = conn.prepareStatement("select * from ? where id = ?")){
           ps.setString(1, table.getNombre());
           ps.setInt(2, id);

           ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();

           if (exists) {
               return 1;
           } else {
               return 0;
           }
        } catch (SQLException exception) {
            return -1;
        }
    }
}
