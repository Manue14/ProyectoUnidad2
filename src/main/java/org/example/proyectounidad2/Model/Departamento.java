package org.example.proyectounidad2.Model;

import java.util.Objects;

public class Departamento {
    private int id;
    private String nombre;
    private String descripcion;

    public Departamento(){}

    public Departamento(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    //Sobrecarga de constructores
    public Departamento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same reference
        if (obj == null || getClass() != obj.getClass()) return false; // Null or type mismatch
        Departamento other = (Departamento) obj;
        return id == other.id; // Compare based on unique ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Consistent with equals()
    }
}
