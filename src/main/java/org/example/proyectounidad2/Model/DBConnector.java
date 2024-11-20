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
    
    public boolean updateAutor(Autor autor) {
        try (PreparedStatement ps = this.conn.prepareStatement("UPDATE Autores "
            + "SET (nombre, apellido1, apellido2, nacimiento, fallecimiento, nacionalidad, foto) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?) "
            + "WEHRE id = ?");) {
            if (checkIfIdExists(autor.getId(), Table.AUTORES) == 0) {
                throw new SQLException("El autor con id " + autor.getId() + " no existe");
            } else if (checkIfIdExists(autor.getId(), Table.AUTORES) == -1) {
                throw new SQLException("Error al conectar con la base de datos");
            }
            
            Mapper.bindAutorUpdateQuery(ps, autor);
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows != 0) {
                return true;
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
                    throw new SQLException("Error al actualizar la obra con id " + obra.getId());
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
    
    //public void filterObras()

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
