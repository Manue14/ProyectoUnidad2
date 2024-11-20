package org.example.proyectounidad2.Model;

import java.sql.*;
import java.util.ArrayList;

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

    //----Métodos de creación---------------------------------------------------------------------------
    public Autor createAutor(Autor autor) {
        try {
            try (PreparedStatement ps = this.conn.prepareStatement(
                    "INSERT INTO Autor (nombre, apellido1, apellido2, nacimiento, fallecimiento, nacionalidad, foto) "
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
                    "INSERT INTO Obra (titulo, alto, ancho, imagen, popular, medio, categoria, fecha, descripcion, "
                         +   "id_autor, id_departamento, id_movimineto) "
                         +   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ) {
                this.conn.setAutoCommit(false);

                if (checkIfIdExists(obra.getId_autor(), Table.AUTORES) != 1) {
                    throw new SQLException("El autor con id " + obra.getId_autor() + " no existe");
                }
                if (checkIfIdExists(obra.getId_departamento(), Table.DEPARTAMENTOS) != 1) {
                    throw new SQLException("El departamento con id " + obra.getId_departamento() + " no existe");
                }
                if (obra.getId_movimiento() != 0 && //Si la obra no tiene un movimiento asignado el valor por defecto es 0 porque ese es el valor por defecto de un int no inicializado
                        checkIfIdExists(obra.getId_movimiento(), Table.MOVIMIENTOS) != 1) {
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM ?");) {
            ArrayList<Object> list = new ArrayList<>();

            ps.setString(1, table.getNombre());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (table == Table.AUTORES) {
                    list.add(Mapper.mapAutor(rs));
                }
                if (table == Table.DEPARTAMENTOS) {
                    list.add(Mapper.mapDepartamento(rs));
                }
                if (table == Table.MOVIMIENTOS) {
                    list.add(Mapper.mapObra(rs));
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

    public Autor getAutorById(int id) {
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Autor WHERE id = ?")) {
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
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Departamento WHERE id = ?")) {
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
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Movimiento WHERE id = ?")) {
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
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Obra WHERE id = ?")) {
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

    public void filterAutores(QueryFieldsObjectAutor fields) {
        
    }

    public ArrayList<Obra> filterObras(QueryFieldsObjectObra fields) {
        ArrayList<Obra> obras = new ArrayList<>();
        try (PreparedStatement ps = this.conn.prepareStatement(
                "SELECT * FROM Obras WHERE titulo LIKE ? AND WHERE autor_id LIKE ? " +
                        "AND WHERE departamento_id LIKE ? AND WHERE movimiento_id LIKE ? " +
                        "AND WHERE categoria like ? AND WHERE popular LIKE ?");
        ) {
            ps.setString(1, "%");
            ps.setString(2, "%");
            ps.setString(3, "%");
            ps.setString(4, "%");
            ps.setString(5, "%");
            ps.setString(6, "%");

            if(fields.getTitulo() != null) {
                ps.setString(1, "%" + fields.getTitulo() + "%");
            }
            if(fields.getAutor_id() != 0) {
                ps.setString(2, Integer.toString(fields.getAutor_id()));
            }
            if(fields.getDepartamento_id() != 0) {
                ps.setString(3, Integer.toString(fields.getDepartamento_id()));
            }
            if(fields.getMovimiento_id() != 0) {
                ps.setString(4, Integer.toString(fields.getMovimiento_id()));
            }
            if(fields.getCategoria() != null) {
                ps.setString(5, fields.getCategoria().getValor());
            }
            if(fields.getPopular() != null) {
                ps.setString(6, Boolean.toString(fields.getPopular()));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                obras.add(Mapper.mapObra(rs));
            }
            rs.close();
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
        return obras;
    }
    //----End métodos de lectura------------------------------------------------------------------------

    //----Métodos de actualización----------------------------------------------------------------------
    public boolean updateAutor(Autor autor) {
        try {
            try (PreparedStatement ps = this.conn.prepareStatement("UPDATE Autores "
                    + "SET (nombre, apellido1, apellido2, nacimiento, fallecimiento, nacionalidad, foto) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?) "
                    + "WHERE id = ?");) {
                this.conn.setAutoCommit(false);

                if (checkIfIdExists(autor.getId(), Table.AUTORES) == 0) {
                    throw new SQLException("El autor con id " + autor.getId() + " no existe");
                } else if (checkIfIdExists(autor.getId(), Table.AUTORES) == -1) {
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
                    + "SET (titulo, alto, ancho, imagen, popular, medio, categoria, "
                    + "fecha, descripcion, id_autor, id_departamento, id_movimiento) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                    + "WHERE id = ?");) {
                this.conn.setAutoCommit(false);

                if (checkIfIdExists(obra.getId(), Table.OBRAS) == 0) {
                    throw new SQLException("La obra con id " + obra.getId() + " no existe");
                } else if (checkIfIdExists(obra.getId(), Table.OBRAS) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }

                if (checkIfIdExists(obra.getId_autor(), Table.AUTORES) == 0) {
                    throw new SQLException("El autor con id " + obra.getId_autor() + " no existe");
                } else if (checkIfIdExists(obra.getId_autor(), Table.AUTORES) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }

                if (checkIfIdExists(obra.getId_departamento(), Table.DEPARTAMENTOS) == 0) {
                    throw new SQLException("El departamento con id " + obra.getId_departamento() + " no existe");
                } else if (checkIfIdExists(obra.getId_departamento(), Table.DEPARTAMENTOS) == -1) {
                    throw new SQLException("Error al conectar con la base de datos");
                }

                if (checkIfIdExists(obra.getId_movimiento(), Table.MOVIMIENTOS) == 0) {
                    throw new SQLException("El movimiento con id " + obra.getId_movimiento() + " no existe");
                } else if (checkIfIdExists(obra.getId_movimiento(), Table.MOVIMIENTOS) == -1) {
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
    //----End métodos de actualización------------------------------------------------------------------

    //----Métodos de eliminación------------------------------------------------------------------------

    //----End métodos de eliminación--------------------------------------------------------------------

    //----Métodos de comprobaciones---------------------------------------------------------------------
    public int checkIfIdExists(int id, Table table) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM ? WHERE id = ?")){
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
    //----End métodos de comprobaciones-----------------------------------------------------------------

}
