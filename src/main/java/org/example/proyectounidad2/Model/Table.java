package org.example.proyectounidad2.Model;

public enum Table {
    AUTORES("Autores"),
    DEPARTAMENTOS("Departamentos"),
    MOVIMIENTOS("Movimientos"),
    OBRAS("Obras");

    private String nombre;

    private Table(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
