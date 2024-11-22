package org.example.DAOs;

public class Desarrolladores {

    private int id; // Clave primaria
    private int juegoId; // ID del juego relacionado
    private String nombre; // Nombre del desarrollador

    // Constructor vacío
    public Desarrolladores() {}

    // Constructor con parámetros
    public Desarrolladores(int id, int juegoId, String nombre) {
        this.id = id;
        this.juegoId = juegoId;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Desarrolladores{" +
                "id=" + id +
                ", juegoId=" + juegoId +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}


