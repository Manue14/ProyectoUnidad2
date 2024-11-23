package org.example.proyectounidad2.Model;

public class Obra {
    private int id;
    private String titulo;
    private float alto;
    private float ancho;
    private byte[] img;
    private boolean popular;
    private String medio;
    private Categoria categoria;
    private String fecha;
    private String descripcion;
    private int id_autor = 1;   //El valor por defecto es 1 ya que se corresponde a un autor anónimo
    private int id_departamento;
    private int id_movimiento;

    public Obra(){}

    public Obra(int id, String titulo, float ancho, float alto, byte[] img, boolean popular, String medio,
                Categoria categoria, String fecha, String descripcion, int id_autor, int id_departamento,
                int id_movimiento) {
        this.id = id;
        this.titulo = titulo;
        this.ancho = ancho;
        this.alto = alto;
        this.img = img;
        this.popular = popular;
        this.medio = medio;
        this.categoria = categoria;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.id_autor = id_autor;
        this.id_departamento = id_departamento;
        this.id_movimiento = id_movimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getAlto() {
        return alto;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public String getMedidas() {
        return this.alto + "x" + this.ancho;
    }

    @Override
    public String toString() {
        return "Id: " + this.id +
                "\nTitulo: '" + this.titulo +
                "\nAlto: " + this.alto +
                "\nAncho: " + this.ancho +
                "\nImagen: " + this.img +
                "\nPpopular: " + this.popular +
                "\nMedio: " + this.medio +
                "\nCategoria: " + this.categoria +
                "\nFecha: " + this.fecha +
                "\nDescripcion: " + this.descripcion +
                "\nId del autor: " + this.id_autor +
                "\nId del departamento: " + this.id_departamento +
                "\nId del movimiento: " + this.id_movimiento;
    }

    //Sobrecarga constructores
    public Obra(String titulo, float ancho, float alto, byte[] img, boolean popular, String medio,
                Categoria categoria, String fecha, String descripcion, int id_autor, int id_departamento,
                int id_movimiento) {
        this.titulo = titulo;
        this.ancho = ancho;
        this.alto = alto;
        this.img = img;
        this.popular = popular;
        this.medio = medio;
        this.categoria = categoria;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.id_autor = id_autor;
        this.id_departamento = id_departamento;
        this.id_movimiento = id_movimiento;
    }

    public Obra(int id, String titulo, float ancho, float alto, byte[] img, boolean popular, String medio,
                Categoria categoria, String fecha, String descripcion, int id_departamento, int id_movimiento) {
        this.id = id;
        this.titulo = titulo;
        this.ancho = ancho;
        this.alto = alto;
        this.img = img;
        this.popular = popular;
        this.medio = medio;
        this.categoria = categoria;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.id_departamento = id_departamento;
        this.id_movimiento = id_movimiento;
    }

    public Obra(String titulo, float ancho, float alto, byte[] img, boolean popular, String medio,
                Categoria categoria, String fecha, String descripcion, int id_departamento, int id_movimiento) {
        this.titulo = titulo;
        this.ancho = ancho;
        this.alto = alto;
        this.img = img;
        this.popular = popular;
        this.medio = medio;
        this.categoria = categoria;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.id_departamento = id_departamento;
        this.id_movimiento = id_movimiento;
    }
}
