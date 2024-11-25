package org.example.DAOs;

import org.example.entidades.JuegosGenerados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class daoJuegosGenerados {
    private static Connection connection;

    public daoJuegosGenerados(Connection connection) {
        this.connection = connection;
    }



    // Leer una relación de juegos generados por juego ID
    public JuegosGenerados leerJuegosGenerados(int juegoId) throws SQLException {
        String sql = "SELECT * FROM juegos_generados WHERE juego_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, juegoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new JuegosGenerados(
                        rs.getInt("juego_id"),
                        rs.getString("generos")
                );
            }
        }
        return null;
    }

    // Obtener todas las relaciones de juegos generados
    public List<JuegosGenerados> obtenerTodos() throws SQLException {
        List<JuegosGenerados> juegosGeneradosList = new ArrayList<>();
        String sql = "SELECT * FROM juegos_generados";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                juegosGeneradosList.add(new JuegosGenerados(
                        rs.getInt("juego_id"),
                        rs.getString("generos")
                ));
            }
        }
        return juegosGeneradosList;
    }

    public List<JuegosGenerados> obtenerJuegosPorGenero(String nombreGenero) throws SQLException {
        List<JuegosGenerados> juegosGeneradosList = new ArrayList<>();
        String sql = "SELECT * FROM juegos_generados WHERE generos = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreGenero);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                juegosGeneradosList.add(new JuegosGenerados(
                        rs.getInt("juego_id"),
                        rs.getString("generos")
                ));
            }
        }
        return juegosGeneradosList;
    }

}
