package org.example.DAOs;

public class Rating {

    private int juegoId; // ID del juego
    private double rating; // Calificación del juego
    private int numberOfReviews; // Número de reseñas del juego

    // Constructor vacío
    public Rating() {}

    // Constructor con parámetros
    public Rating(int juegoId, double rating, int numberOfReviews) {
        this.juegoId = juegoId;
        this.rating = rating;
        this.numberOfReviews = numberOfReviews;
    }

    // Getters y Setters
    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Rating{" +
                "juegoId=" + juegoId +
                ", rating=" + rating +
                ", numberOfReviews=" + numberOfReviews +
                '}';
    }
}
