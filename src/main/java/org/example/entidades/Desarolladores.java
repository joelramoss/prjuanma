package org.example.entidades;

public class Desarolladores {
    int id;
    String nombre;

    // Constructor vacío
    public Desarolladores() {}

    // Constructor con parámetros
    public Desarolladores(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Desarolladores{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
