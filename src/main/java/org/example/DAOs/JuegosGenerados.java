package org.example.DAOs;

public class JuegosGenerados {

    private int juegoId; // ID del juego
    private String generos; // Lista de géneros asociados al juego

    // Constructor vacío
    public JuegosGenerados() {}

    // Constructor con parámetros
    public JuegosGenerados(int juegoId, String generos) {
        this.juegoId = juegoId;
        this.generos = generos;
    }

    // Getters y Setters
    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }

    // Método toString()
    @Override
    public String toString() {
        return "JuegosGenerados{" +
                "juegoId=" + juegoId +
                ", generos='" + generos + '\'' +
                '}';
    }
}
