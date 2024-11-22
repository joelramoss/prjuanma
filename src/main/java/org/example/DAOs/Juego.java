package org.example.DAOs;

import java.util.Date;

public class Juego {

    private int id; // ID del juego
    private String title; // Título del juego
    private Date releaseDate; // Fecha de lanzamiento
    private String summary; // Resumen del juego
    private int plays; // Cantidad de veces jugado
    private int playing; // Número de jugadores actualmente jugando
    private int backlogs; // Juegos en espera
    private int wishlist; // Juegos en lista de deseos

    // Constructor vacío
    public Juego() {}

    // Constructor con parámetros
    public Juego(int id, String title, Date releaseDate, String summary, int plays, int playing, int backlogs, int wishlist) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.plays = plays;
        this.playing = playing;
        this.backlogs = backlogs;
        this.wishlist = wishlist;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public int getPlaying() {
        return playing;
    }

    public void setPlaying(int playing) {
        this.playing = playing;
    }

    public int getBacklogs() {
        return backlogs;
    }

    public void setBacklogs(int backlogs) {
        this.backlogs = backlogs;
    }

    public int getWishlist() {
        return wishlist;
    }

    public void setWishlist(int wishlist) {
        this.wishlist = wishlist;
    }

    @Override
    public  String toString() { // Método toString()
        return "Juego{" +
                "id=" + id +
                ", Título='" + title + '\'' +
                ", Fecha de lanzamiento=" + releaseDate +
                ", Resumen='" + summary + '\'' +
                ", Juegos jugados=" + plays +
                ", Juegos en juego=" + playing +
                ", Juegos pendientes=" + backlogs +
                ", Lista de deseos=" + wishlist +
                '}';
    }
}
