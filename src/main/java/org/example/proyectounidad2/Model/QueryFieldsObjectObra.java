package org.example.proyectounidad2.Model;

public class QueryFieldsObjectObra {
    private String titulo = null;
    private int autor_id = 0;
    private int departamento_id = 0;
    private int movimiento_id = 0;
    private Categoria categoria = null;
    private Boolean popular = null;

    public QueryFieldsObjectObra() {}

    public QueryFieldsObjectObra(String titulo, int autor_id, int departamento_id, int movimiento_id, Categoria categoria, boolean popular) {
        this.titulo = titulo;
        this.autor_id = autor_id;
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

    public int getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(int autor_id) {
        this.autor_id = autor_id;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean getPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }
}
