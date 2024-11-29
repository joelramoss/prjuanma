package org.example.entidades;

public class juego_equipo {

    private int id; // Clave primaria
    private int juegoId; // ID del juego relacionado
    private int desarrolladorId; // ID del desarrollador relacionado

    // Constructor vacío
    public juego_equipo() {}

    // Constructor con parámetros
    public juego_equipo(int id, int juegoId, int desarrolladorId) {
        this.id = id;
        this.juegoId = juegoId;
        this.desarrolladorId = desarrolladorId;
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

    public int getDesarrolladorId() {
        return desarrolladorId;
    }

    public void setDesarrolladorId(int desarrolladorId) {
        this.desarrolladorId = desarrolladorId;
    }

    @Override
    public String toString() {
        return "juego_equipo{" +
                "id=" + id +
                ", juegoId=" + juegoId +
                ", desarrolladorId=" + desarrolladorId +
                '}';
    }
}
