package org.example.DAOs;
import org.example.entidades.Generos;

import java.sql.*;

public class daoGeneros {

    private Connection connection;

    public daoGeneros(Connection connection) {
        this.connection = connection;
    }

    // Crear un nuevo género
    public void crearGenero(Generos genero) throws SQLException {
        String sql = "INSERT INTO Generos (Genero) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, genero.getGeneros());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    genero.setId(keys.getInt(1));
                }
            }
        }
    }

    // Leer un género por ID
    public Generos leerGenero(int id) throws SQLException {
        String sql = "SELECT * FROM genero WHERE id = ?";
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
        String sql = "UPDATE generos SET genero = ? WHERE id = ?";
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
