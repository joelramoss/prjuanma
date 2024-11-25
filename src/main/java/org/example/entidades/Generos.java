package org.example.entidades;

public class Generos {

    private int id; // ID del género como clave primaria
    private String generos; // Lista de géneros asociados al juego

    // Constructor vacío
    public Generos() {}


    // Constructor con parámetros
    public Generos(int id, String generos) {
        this.id = id;
        this.generos = generos;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Generos{" +
                "id=" + id +
                ", generos='" + generos + '\'' +
                '}';
    }
}
