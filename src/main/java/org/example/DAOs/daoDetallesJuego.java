package org.example.DAOs;

import org.example.entidades.DetallesJuego;
import java.sql.*;

public class daoDetallesJuego {

    private Connection connection;

    // Constructor
    public daoDetallesJuego(Connection connection) {
        this.connection = connection;
    }

    // Crear un nuevo DetallesJuego
    public void crearDetallesJuego(DetallesJuego detallesJuego) {
        String query = "INSERT INTO detalles_juego (ID, Reviews) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, detallesJuego.getJuegoId());
            stmt.setString(2, detallesJuego.getReviews());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar detalles del juego: " + e.getMessage());
        }
    }

    // Obtener DetallesJuego por ID
    public DetallesJuego obtenerDetallesJuegoPorId(int juegoId) {
        String query = "SELECT ID, Reviews FROM detalles_juego WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, juegoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DetallesJuego(
                        rs.getInt("ID"),
                        rs.getString("Reviews")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener detalles del juego: " + e.getMessage());
        }
        return null;
    }

    // Actualizar DetallesJuego
    public void actualizarDetallesJuego(DetallesJuego detallesJuego) {
        String query = "UPDATE detalles_juego SET Reviews = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, detallesJuego.getReviews());
            stmt.setInt(2, detallesJuego.getJuegoId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar detalles del juego: " + e.getMessage());
        }
    }

    // Eliminar DetallesJuego por ID
    public void eliminarDetallesJuego(int juegoId) {
        String query = "DELETE FROM detalles_juego WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, juegoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalles del juego: " + e.getMessage());
        }
    }
}
