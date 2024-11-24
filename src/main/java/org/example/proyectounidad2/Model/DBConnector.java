package org.example.proyectounidad2.Model;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {
    private String url = "jdbc:mysql://localhost:3306/Coleccion";
    private Connection conn;

    public DBConnector() throws SQLException{
        this.conn = DriverManager.getConnection(url, "manu", "abc123.");
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    //----Métodos de creación---------------------------------------------------------------------------
    public Autor createAutor(Autor autor) {
        try {
            try (PreparedStatement ps = this.conn.prepareStatement(
                    "INSERT INTO Autores (nombre, apellido1, apellido2, nacimiento, fallecimiento, nacionalidad, foto) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            ) {
                this.conn.setAutoCommit(false);

                Mapper.bindAutorCreateQuery(ps, autor);
                int affected_rows = ps.executeUpdate();
                if (affected_rows == 1) {
                    this.conn.commit();
                    return autor;
                } else {
                    throw new SQLException("Error extraño al insertar el autor");
                }
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
                this.conn.rollback();
            } finally {
                this.conn.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public Obra createObra(Obra obra) {
        try {
            try (PreparedStatement ps = this.conn.prepareStatement(
                    "INSERT INTO Obras (titulo, alto, ancho, imagen, popular, medio, categoria, fecha, descripcion, "
                         +   "id_autor, id_departamento, id_movimiento) "
                         +   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ) {
                this.conn.setAutoCommit(false);

                if (checkIfIdExists(obra.getId_autor(), Table.AUTORES.getNombre()) != 1) {
                    throw new SQLException("El autor con id " + obra.getId_autor() + " no existe");
                }
                if (checkIfIdExists(obra.getId_departamento(), Table.DEPARTAMENTOS.getNombre()) != 1) {
                    throw new SQLException("El departamento con id " + obra.getId_departamento() + " no existe");
                }
                if (obra.getId_movimiento() != 0 && //Si la obra no tiene un movimiento asignado el valor por defecto es 0 porque ese es el valor por defecto de un int no inicializado
                        checkIfIdExists(obra.getId_movimiento(), Table.MOVIMIENTOS.getNombre()) != 1) {
                    throw new SQLException("El movimiento con id " + obra.getId_movimiento() + " no existe");
                }

                Mapper.bindObraCreateQuery(ps, obra);
                int affected_rows = ps.executeUpdate();
                if (affected_rows == 1) {
                    this.conn.commit();
                    return obra;
                } else {
                    throw new SQLException("Error extraño al insertar la obra");
                }
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
                this.conn.rollback();
            } finally {
                this.conn.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }
    //----End métodos de creación-----------------------------------------------------------------------

    //----Métodos de lectura----------------------------------------------------------------------------
    public ArrayList<Object> getAllFromTable(Table table) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table.getNombre())) {
            ArrayList<Object> list = new ArrayList<>();


            ResultSet rs = ps.executeQuery();
            System.out.println("Ejecutado select from "+table.getNombre());
            while (rs.next()) {
                /*IMPRIME LOS DATOS DEL RESULT SET
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "\t"); // Print each column value
                }
                System.out.println();
                */
                // Add data to the list based on the table type
                if (table == Table.AUTORES) {
                    list.add(Mapper.mapAutor(rs));
                }
                if (table == Table.DEPARTAMENTOS) {
                    list.add(Mapper.mapDepartamento(rs));
                }
                if (table == Table.MOVIMIENTOS) {
                    list.add(Mapper.mapMovimiento(rs));
                }
                if (table == Table.OBRAS) {
                    list.add(Mapper.mapObra(rs));

                }
            }
            rs.close();

            return list;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
    }
    
    public ArrayList<Autor> getAllAutores() {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Autores")) {
            ArrayList<Autor> autores = new ArrayList<>();


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                autores.add(Mapper.mapAutor(rs));
            }
            rs.close();

            return autores;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
    }
    
    public ArrayList<Departamento> getAllDepartamentos() {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Departamentos")) {
            ArrayList<Departamento> departamentos = new ArrayList<>();


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                departamentos.add(Mapper.mapDepartamento(rs));
            }
            rs.close();

            return departamentos;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
    }
    
    public ArrayList<Movimiento> getAllMovimientos() {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Movimientos")) {
            ArrayList<Movimiento> movimientos = new ArrayList<>();


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                movimientos.add(Mapper.mapMovimiento(rs));
            }
            rs.close();

            return movimientos;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
    }
    
    public ArrayList<Obra> getAllObras() {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Obras")) {
            ArrayList<Obra> obras = new ArrayList<>();


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                obras.add(Mapper.mapObra(rs));
            }
            rs.close();

            return obras;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
    }

    public Autor getAutorById(int id) {
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Autores WHERE id = ?")) {
            if (checkIfIdExists(id, Table.AUTORES.getNombre()) == 0) {
                throw new SQLException("El autor con id " + id + " no existe");
            } else if (checkIfIdExists(id, Table.AUTORES.getNombre()) == -1) {
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
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Departamentos WHERE id = ?")) {
            if (checkIfIdExists(id, Table.DEPARTAMENTOS.getNombre()) == 0) {
                throw new SQLException("El departamento con id " + id + " no existe");
            } else if (checkIfIdExists(id, Table.DEPARTAMENTOS.getNombre()) == -1) {
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
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Movimientos WHERE id = ?")) {
            if (checkIfIdExists(id, Table.MOVIMIENTOS.getNombre()) == 0) {
                throw new SQLException("El movimiento con id " + id + " no existe");
            } else if (checkIfIdExists(id, Table.MOVIMIENTOS.getNombre()) == -1) {
                throw new SQLException("Error al conectar con la base de datos");
            }

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Movimiento movimiento = Mapper.mapMovimiento(rs);
            rs.close();

            return movimiento;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public Obra getObraById(int id) {
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Obras WHERE id = ?")) {
            if (checkIfIdExists(id, Table.OBRAS.getNombre()) == 0) {
                throw new SQLException("La obra con id " + id + " no existe");
            } else if (checkIfIdExists(id, Table.OBRAS.getNombre()) == -1) {
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

    public ArrayList<String> getNacionalidades(){
        try(PreparedStatement ps = this.conn.prepareStatement("SELECT nacionalidad FROM Autores GROUP BY nacionalidad")) {
            ArrayList<String> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                /*IMPRIME LOS DATOS DEL RESULT SET
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "\t"); // Print each column value
                }
                System.out.println();
                */
                // Add data to the list based on the table type
                list.add(rs.getString("nacionalidad"));

            }
            rs.close();

            return list;
        }catch (SQLException exception){
            System.err.println(exception.getMessage());
            return null;
        }
    }

    public ArrayList<Autor> filterAutores(QueryFieldsObjectAutor fields) {
        ArrayList<Autor> autores = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Autores WHERE 1=1 "); // Start with a valid condition

        // Build the query dynamically based on the input fields
        if (fields.getNombre() != null && !fields.getNombre().isEmpty()) {
            query.append("AND nombre LIKE ? ");
        }
        if (fields.getApellido1() != null && !fields.getApellido1().isEmpty()) {
            query.append("AND apellido1 LIKE ? ");
        }
        if (fields.getApellido2() != null && !fields.getApellido2().isEmpty()) {
            query.append("AND apellido2 LIKE ? ");
        }
        if (fields.getNacionalidad() != null && !fields.getNacionalidad().isEmpty()) {
            query.append("AND nacionalidad LIKE ? ");
        }

        try (PreparedStatement ps = this.conn.prepareStatement(query.toString())) {
            int index = 1;

            // Set the parameters dynamically based on which fields are not null
            if (fields.getNombre() != null && !fields.getNombre().isEmpty()) {
                ps.setString(index++, "%" + fields.getNombre() + "%");
            }
            if (fields.getApellido1() != null && !fields.getApellido1().isEmpty()) {
                ps.setString(index++, "%" + fields.getApellido1() + "%");
            }
            if (fields.getApellido2() != null && !fields.getApellido2().isEmpty()) {
                ps.setString(index++, "%" + fields.getApellido2() + "%");
            }
            if (fields.getNacionalidad() != null && !fields.getNacionalidad().isEmpty()) {
                ps.setString(index++, "%" + fields.getNacionalidad() + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                autores.add(Mapper.mapAutor(rs));
            }
            rs.close();
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
        return autores;
    }


    public ArrayList<Obra> filterObras(QueryFieldsObjectObra fields) {
        ArrayList<Obra> obras = new ArrayList<>();
        
        try (CallableStatement cs = this.conn.prepareCall(
                "{CALL filter_obras(?,?,?,?,?,?)}"
        );) {
            cs.setString(1, fields.getTitulo());
            cs.setString(2, fields.getAutor_nombre());
            cs.setInt(3, fields.getDepartamento_id());
            cs.setInt(4, fields.getMovimiento_id());
            cs.setString(5, fields.getCategoria());
            cs.setBoolean(6, fields.getPopular());
            
            ResultSet rs = cs.executeQuery();
            System.out.println(cs);
            while (rs.next()) {
                obras.add(Mapper.mapObra(rs));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Error al filtrar obras: " + exception.getMessage());
            return null;
        }
        
        return obras;
    }





    public int obtenerIdAutorPorNombre(String nombreAutor) {
        int autorId = 0;  // Valor por defecto si no se encuentra el autor

        // Realizar la consulta para obtener el ID del autor basado en su nombre
        try {
            String sql = "SELECT id FROM Autores WHERE nombre LIKE ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + nombreAutor + "%");  // Búsqueda parcial por nombre
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    autorId = rs.getInt("id");  // Obtener el ID
                }
            }
        } catch (SQLException exception) {
            System.err.println("Error al obtener el ID del autor: " + exception.getMessage());
        }

        return autorId;
    }




    public ArrayList<String> getAllNacionalidades() {
        ArrayList<String> nacionalidades = new ArrayList<>();
        
        try (PreparedStatement ps = this.conn.prepareStatement(
                "SELECT DISTINCT nacionalidad FROM Autores"
        );){
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                nacionalidades.add(rs.getString("nacionalidad"));
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
        return nacionalidades;
    }

    //----End métodos de lectura------------------------------------------------------------------------

    //----Métodos de actualización----------------------------------------------------------------------
    public boolean updateAutor(Autor autor) {
        try {
            try (PreparedStatement ps = this.conn.prepareStatement("UPDATE Autores " +
                    "SET nombre = ?, apellido1 = ?, apellido2 = ?, nacimiento = ?, fallecimiento = ?, nacionalidad = ?," +
                    " foto = ? WHERE id = ?");) {
                this.conn.setAutoCommit(false);

                if (checkIfIdExists(autor.getId(), Table.AUTORES.getNombre()) == 0) {
                    throw new SQLException("El autor con id " + autor.getId() + " no existe");
                } else if (checkIfIdExists(autor.getId(), Table.AUTORES.getNombre()) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }

                Mapper.bindAutorUpdateQuery(ps, autor);

                int affectedRows = ps.executeUpdate();
                if (affectedRows == 1) {
                    this.conn.commit();
                    return true;
                } else {
                    throw new SQLException("Error extraño al actualizar el autor con id " + autor.getId());
                }
            } catch (SQLException exception) {
                this.conn.rollback();
                System.err.println(exception.getMessage());
            } finally {
                this.conn.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return false;
    }

    public boolean updateObra(Obra obra) {
        try {
            try (PreparedStatement ps = this.conn.prepareStatement("UPDATE Obras "
                    + "SET titulo = ?, alto = ?, ancho = ?, imagen = ?, popular = ?, medio = ?, categoria = ?, "
                    + "fecha = ?, descripcion = ?, id_autor = ?, id_departamento = ?, id_movimiento = ? "
                    + "WHERE id = ?");) {
                this.conn.setAutoCommit(false);

                if (checkIfIdExists(obra.getId(), Table.OBRAS.getNombre()) == 0) {
                    throw new SQLException("La obra con id " + obra.getId() + " no existe");
                } else if (checkIfIdExists(obra.getId(), Table.OBRAS.getNombre()) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }

                if (checkIfIdExists(obra.getId_autor(), Table.AUTORES.getNombre()) == 0) {
                    throw new SQLException("El autor con id " + obra.getId_autor() + " no existe");
                } else if (checkIfIdExists(obra.getId_autor(), Table.AUTORES.getNombre()) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }

                if (checkIfIdExists(obra.getId_departamento(), Table.DEPARTAMENTOS.getNombre()) == 0) {
                    throw new SQLException("El departamento con id " + obra.getId_departamento() + " no existe");
                } else if (checkIfIdExists(obra.getId_departamento(), Table.DEPARTAMENTOS.getNombre()) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }

                if (checkIfIdExists(obra.getId_movimiento(), Table.MOVIMIENTOS.getNombre()) == 0) {
                    throw new SQLException("El movimiento con id " + obra.getId_movimiento() + " no existe");
                } else if (checkIfIdExists(obra.getId_movimiento(), Table.MOVIMIENTOS.getNombre()) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }

                Mapper.bindObraUpdateQuery(ps, obra);
         
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 1) {
                    this.conn.commit();
                    return true;
                } else {
                    throw new SQLException("Error extraño al actualizar la obra con id " + obra.getId());
                }

            } catch (SQLException exception) {
                System.err.println("Error al actualizar la obra: " + exception.getMessage());
                this.conn.rollback();
            } finally {
                this.conn.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return false;
    }
    //----End métodos de actualización------------------------------------------------------------------

    //----Métodos de eliminación------------------------------------------------------------------------
    public boolean deleteAutor(int id) {
        try {
            try (PreparedStatement ps = this.conn.prepareStatement(
                    "DELETE FROM Autores WHERE id = ?"
            );) {
                this.conn.setAutoCommit(false);
                
                if (checkIfIdExists(id, Table.AUTORES.getNombre()) == 0) {
                    throw new SQLException("El autor con id " + id + " no existe");
                } else if (checkIfIdExists(id, Table.AUTORES.getNombre()) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }
                
                ps.setInt(1, id);
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 1) {
                    this.conn.commit();
                    return true;
                } else {
                    throw new SQLException("Error extraño al borrar autor con id " + id);
                }
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
                this.conn.rollback();
            } finally {
                this.conn.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return false;
    }
    
    public boolean deleteObra(Obra obra) {
        try {
            try (PreparedStatement ps = this.conn.prepareStatement(
                    "DELETE FROM Obras WHERE id = ?");
                CallableStatement cs = this.conn.prepareCall(
                        "{CALL del_autor_if_not_obras(?)}");
                ) {
                this.conn.setAutoCommit(false);
                
                if (checkIfIdExists(obra.getId(), Table.OBRAS.getNombre()) == 0) {
                    throw new SQLException("La obra con id " + obra.getId() + " no existe");
                } else if (checkIfIdExists(obra.getId(), Table.OBRAS.getNombre()) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }
                
                ps.setInt(1, obra.getId());
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 1) {
                    cs.setInt(1, obra.getId_autor());
                    affectedRows = cs.executeUpdate();
                    if (affectedRows <= 1) {
                        this.conn.commit();
                        return true;
                    } else {
                        throw new SQLException("Error extraño al borrar autor sin obras restantes");
                    }
                    
                } else {
                    throw new SQLException("Error extraño al borrar la obra con id " + obra.getId());
                }
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
                this.conn.rollback();
            } finally {
                this.conn.setAutoCommit(true);
            }
            
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return false;
    }
    //----End métodos de eliminación--------------------------------------------------------------------

    //----Métodos de comprobaciones---------------------------------------------------------------------
    public int checkIfIdExists(int id, String table) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE id = ?")){
           ps.setInt(1, id);

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
    //----End métodos de comprobaciones-----------------------------------------------------------------
}
