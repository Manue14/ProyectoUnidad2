package org.example.proyectounidad2.Model;

import java.time.LocalDate;

public class Autor {
    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate nacimiento;
    private LocalDate fallecimiento;
    private String nacionalidad;
    private byte[] foto;

    public Autor(){}

    public Autor(int id, String nombre, String apellido1, String apellido2, LocalDate nacimiento,
                 LocalDate fallecimiento, String nacionalidad, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
        this.nacionalidad = nacionalidad;
        this.foto = foto;
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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public LocalDate getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(LocalDate fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido1 + " " + this.apellido2;
    }

    public String getFechas() {
        return this.nacimiento + (this.fallecimiento != null ? "-" + this.fallecimiento : "");
    }

    @Override
    public String toString() {
        return "Id: " + this.id +
                "\nNombre: " + this.nombre +
                "\nApellido1: " + this.apellido1 +
                "\nApellido2: " + this.apellido2 +
                "\nNacimiento: " + this.nacimiento +
                "\nFallecimiento: " + this.fallecimiento +
                "\nNacionalidad: " + this.nacionalidad;
    }

    //Sobrecarga de constructores
    public Autor(String nombre, String apellido1, String apellido2, LocalDate nacimiento,
                 LocalDate fallecimiento, String nacionalidad, byte[] foto) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
        this.nacionalidad = nacionalidad;
        this.foto = foto;
    }

    public Autor(String nombre, String apellido1, String apellido2, LocalDate nacimiento, String nacionalidad) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nacimiento = nacimiento;
        this.nacionalidad = nacionalidad;
    }
}
