package org.example.entidades;

public class juego_equipo {

    private int id; // Clave primaria
    private int juegoId; // ID del juego relacionado


    // Constructor vacío
    public juego_equipo() {}

    // Constructor con parámetros
    public juego_equipo(int id, int juegoId) {
        this.id = id;
        this.juegoId = juegoId;

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


    @Override
    public String toString() {
        return "Desarrolladores{" +
                "id =" + id +
                ", juegoId =" + juegoId +'}';
    }
}


