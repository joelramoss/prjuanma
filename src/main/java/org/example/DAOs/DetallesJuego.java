package org.example.DAOs;

public class DetallesJuego {

    private int juegoId; // ID del juego como clave primaria
    private String reviews; // Almacena las reseñas como texto largo

    // Constructor vacío
    public DetallesJuego() {}

    // Constructor con parámetros
    public DetallesJuego(int juegoId, String reviews) {
        this.juegoId = juegoId;
        this.reviews = reviews;
    }

    // Getters y Setters
    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    // Método toString()
    @Override
    public String toString() {
        return "DetallesJuego{" +
                "juegoId=" + juegoId +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
