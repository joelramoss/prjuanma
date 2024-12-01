package org.example.DAOs;
import org.example.entidades.Rating;

import java.sql.*;

public class daoRating {

    private Connection connection;  // Mantén la variable como no estática

    // Constructor donde se inyecta la conexión
    public daoRating(Connection connection) {
        this.connection = connection;
    }

    // Método ya no es estático
    public void crearRating(Rating rating) throws SQLException {
        String sql = "INSERT INTO rating (ID, rating, number_of_reviews) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rating.getJuegoId());
            stmt.setDouble(2, rating.getRating());
            stmt.setInt(3, rating.getNumberOfReviews());
            stmt.executeUpdate();
        }
    }

    // Leer un rating por juegoId
    public Rating leerRating(int juegoId) throws SQLException {
        String sql = "SELECT * FROM rating WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, juegoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Rating(
                        rs.getInt("ID"),
                        rs.getDouble("rating"),
                        rs.getInt("number_of_reviews")
                );
            }
        }
        return null;
    }

    // Actualizar un rating
    public void actualizarRating(Rating rating) throws SQLException {
        String sql = "UPDATE rating SET rating = ?, number_of_reviews = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, rating.getRating());
            stmt.setInt(2, rating.getNumberOfReviews());
            stmt.setInt(3, rating.getJuegoId());
            stmt.executeUpdate();
        }
    }

    // Eliminar un rating por juegoId
    public void eliminarRating(int juegoId) throws SQLException {
        String sql = "DELETE FROM rating WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, juegoId);
            stmt.executeUpdate();
        }
    }
}
