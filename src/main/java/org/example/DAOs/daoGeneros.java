package org.example.DAOs;

import org.example.entidades.Generos;

import java.sql.*;

public class daoGeneros {

    private Connection connection;

    public daoGeneros(Connection connection) {
        this.connection = connection;
    }

    // Crear un nuevo género (retorna el ID generado)
    public int crearGenero(Generos genero) throws SQLException {
        String sql = "INSERT INTO generos (genero) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, genero.getGeneros());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    genero.setId(id); // Asignamos el ID al objeto
                    return id; // Retornamos el ID generado
                }
            }
        }
        return -1; // Si no se genera un ID, retornamos -1
    }

    // Buscar un género por nombre (retorna el ID si existe, o -1 si no existe)
    public int obtenerGeneroPorNombre(String nombreGenero) throws SQLException {
        String sql = "SELECT id FROM generos WHERE genero = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreGenero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Retorna el ID si existe
                }
            }
        }
        return -1; // Retorna -1 si no encuentra el género
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
                        rs.getString("genero")
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
