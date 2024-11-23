package org.example.proyectounidad2.Model;

public class QueryFieldsObjectObra {
    private String titulo = "";
    private String autor_nombre = "";
    private int departamento_id = 0;
    private int movimiento_id = 0;
    private String categoria = "";
    private Boolean popular = null;

    public QueryFieldsObjectObra() {}

    public QueryFieldsObjectObra(String titulo, String autor_nombre, int departamento_id, int movimiento_id, String categoria, boolean popular) {
        this.titulo = titulo;
        this.autor_nombre = autor_nombre;
        this.departamento_id = departamento_id;
        this.movimiento_id = movimiento_id;
        this.categoria = categoria;
        this.popular = popular;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor_nombre() {
        return autor_nombre;
    }

    public void setAutor_nombre(String autor_nombre) {
        this.autor_nombre = autor_nombre;
    }

    public int getDepartamento_id() {
        return departamento_id;
    }

    public void setDepartamento_id(int departamento_id) {
        this.departamento_id = departamento_id;
    }

    public int getMovimiento_id() {
        return movimiento_id;
    }

    public void setMovimiento_id(int movimiento_id) {
        this.movimiento_id = movimiento_id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }

    @Override
    public String toString() {
        return "QueryFieldsObjectObra{" + "titulo=" + titulo + ", autor_nombre=" + autor_nombre + ", departamento_id=" + departamento_id + ", movimiento_id=" + movimiento_id + ", categoria=" + categoria + ", popular=" + popular + '}';
    }
    
    
}
