package org.example.entidades;

import java.util.Date;
import java.util.Random;

public class Juego {

    private int id; // ID del juego
    private String title; // Título del juego
    private Date releaseDate; // Fecha de lanzamiento
    private String summary; // Resumen del juego
    private int plays; // Cantidad de veces jugado
    private int playing; // Número de jugadores actualmente jugando
    private int backlogs; // Juegos en espera
    private int wishlist; // Juegos en lista de deseos
    private int timesListed; // Número de veces que se ha listado el juego

    // Constructor vacío
    public Juego() {}

    // Constructor con parámetros
    public Juego(int id, String title, Date releaseDate, String summary, int plays, int playing, int backlogs, int wishlist, int timesListed) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.plays = plays;
        this.playing = playing;
        this.backlogs = backlogs;
        this.wishlist = wishlist;
        this.timesListed = timesListed;
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

    public java.sql.Date getReleaseDate() {
        return (java.sql.Date) releaseDate;
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
    public int getTimesListed() {
        return timesListed;
    }
    public void setTimesListed(int timesListed) {
        this.timesListed = timesListed;
    }

    public void generarIDaleatorio() {
        Random random = new Random();

        //ultimo id de la base de datos
        id = random.nextInt(0,0);
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
