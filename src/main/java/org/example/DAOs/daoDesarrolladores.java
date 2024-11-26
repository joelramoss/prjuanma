package org.example.DAOs;

import org.example.entidades.Desarolladores;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class daoDesarrolladores {
    private static Connection connection;

    public daoDesarrolladores(Connection connection) {
        this.connection = connection;
    }

    // Crear un nuevo desarrollador
    public static void crearDesarrollador(Desarolladores desarrollador) throws SQLException {
        String sql = "INSERT INTO Desarolladores (nombre) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, desarrollador.getNombre());
            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    desarrollador.setId(keys.getInt(1));
                }
            }
        }
    }

    // Leer un desarrollador por ID
    public Desarolladores leerDesarrollador(int id) throws SQLException {
        String sql = "SELECT * FROM desarrolladores WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Desarolladores(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
            }
        }
        return null;
    }

    // Obtener todos los desarrolladores
    public List<Desarolladores> obtenerTodos() throws SQLException {
        List<Desarolladores> desarrolladores = new ArrayList<>();
        String sql = "SELECT * FROM desarrolladores";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                desarrolladores.add(new Desarolladores(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }
        }
        return desarrolladores;
    }

    // Actualizar un desarrollador
    public void actualizarDesarrollador(Desarolladores desarrollador) throws SQLException {
        String sql = "UPDATE desarrolladores SET nombre = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, desarrollador.getNombre());
            stmt.setInt(2, desarrollador.getId());
            stmt.executeUpdate();
            System.out.println("Desarrollador actualizado con éxito");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el desarrollador: " + e.getMessage());
        }
    }

    // Eliminar un desarrollador por ID
    public void eliminarDesarrollador(int id) throws SQLException {
        String sql = "DELETE FROM desarrolladores WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}


