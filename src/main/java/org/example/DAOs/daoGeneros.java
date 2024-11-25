package org.example.DAOs;
import org.example.entidades.Generos;

import java.sql.*;

public class daoGeneros {

    private static Connection connection;

    public daoGeneros(Connection connection) {
        this.connection = connection;
    }

    // Crear un nuevo género
    public static void crearGenero(Generos genero) throws SQLException {
        String sql = "INSERT INTO generos (id, generos) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, genero.getId());
            stmt.setString(2, genero.getGeneros());
            stmt.executeUpdate();
        }
    }

    // Leer un género por ID
    public Generos leerGenero(int id) throws SQLException {
        String sql = "SELECT * FROM generos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Generos(
                        rs.getInt("id"),
                        rs.getString("generos")
                );
            }
        }
        return null;
    }

    // Actualizar un género
    public void actualizarGenero(Generos genero) throws SQLException {
        String sql = "UPDATE generos SET generos = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, genero.getGeneros());
            stmt.setInt(2, genero.getId());
            stmt.executeUpdate();
        }
    }

    // Eliminar un género por ID
    public void eliminarGenero(int id) throws SQLException {
        String sql = "DELETE FROM generos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
