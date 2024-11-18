package org.example.proyectounidad2.Model;

import java.time.LocalDate;

public class Movimiento {
    private int id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate fin;
    private String descripcion;

    public Movimiento(){}

    public Movimiento(int id, String nombre, LocalDate inicio, LocalDate fin, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechas() {
        return this.inicio + (this.fin != null ? "-" + this.fin : "");
    }

    @Override
    public String toString() {
        return "Id: " + this.id +
                "\nNombre: " + this.nombre +
                "\nInicio: " + this.inicio +
                "\nFin: " + this.fin +
                "\nDescripcion: " + this.descripcion;
    }

    //Sobrecarga de constructores
    public Movimiento(String nombre, LocalDate inicio, String descripcion) {
        this.inicio = inicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
