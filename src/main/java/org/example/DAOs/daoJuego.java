package org.example.DAOs;

import org.example.ConnectionDB;
import org.example.entidades.Juego;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class daoJuego {
    private static Connection connection;

    static {
        try {
            connection = ConnectionDB.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar la conexión a la base de datos", e);
        }
    }

    // Métodos que usan la conexión estática
    public static void crearJuego(Juego juego) throws SQLException {
        String sql = "INSERT INTO juego (title, release_date, summary, plays, playing, backlogs, wishlist) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, juego.getTitle());
            stmt.setDate(2, new java.sql.Date(juego.getReleaseDate().getTime()));
            stmt.setString(3, juego.getSummary());
            stmt.setInt(4, juego.getPlays());
            stmt.setInt(5, juego.getPlaying());
            stmt.setInt(6, juego.getBacklogs());
            stmt.setInt(7, juego.getWishlist());
            stmt.executeUpdate();
        }
    }

    // Obtener todos los juegos
    public List<Juego> obtenerTodos() throws SQLException {
        List<Juego> juegos = new ArrayList<>();
        String sql = "SELECT * FROM juego";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                juegos.add(new Juego(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDate("release_date"),
                        rs.getString("summary"),
                        rs.getInt("plays"),
                        rs.getInt("playing"),
                        rs.getInt("backlogs"),
                        rs.getInt("wishlist")
                ));
            }
        }
        return juegos;
    }

    // Buscar un juego por su ID
    public Juego obtenerPorId(int id) throws SQLException {
        Juego juego = null;
        String sql = "SELECT * FROM juego WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                juego = new Juego(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDate("release_date"),
                        rs.getString("summary"),
                        rs.getInt("plays"),
                        rs.getInt("playing"),
                        rs.getInt("backlogs"),
                        rs.getInt("wishlist")
                );
            }
        }
        return juego;  // Si no se encuentra el juego, retornará null
    }

    // Actualizar un juego
    public void actualizarJuego(Juego juego) throws SQLException {
        String sql = "UPDATE juego SET title = ?, release_date = ?, summary = ?, plays = ?, playing = ?, backlogs = ?, wishlist = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, juego.getTitle());
            stmt.setDate(2, new java.sql.Date(juego.getReleaseDate().getTime()));
            stmt.setString(3, juego.getSummary());
            stmt.setInt(4, juego.getPlays());
            stmt.setInt(5, juego.getPlaying());
            stmt.setInt(6, juego.getBacklogs());
            stmt.setInt(7, juego.getWishlist());
            stmt.setInt(8, juego.getId());
            stmt.executeUpdate();
            System.out.println("Juego actualizado con éxito");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el juego: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al actualizar el juego: " + e.getMessage());
        }
    }

    // Eliminar un juego por ID
    public void eliminarJuego(int id) throws SQLException {
        String sql = "DELETE FROM juego WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
