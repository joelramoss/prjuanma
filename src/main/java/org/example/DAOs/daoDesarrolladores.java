package org.example.DAOs;
import org.example.entidades.Desarrolladores;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class daoDesarrolladores {
    private static Connection connection;

        public daoDesarrolladores(Connection connection) {
            this.connection = connection;
        }

        public static void crearDesarrollador(Desarrolladores desarrollador) throws SQLException {
            if (connection == null) {
                throw new SQLException("La conexión a la base de datos no está establecida.");
            }

            String sql = "INSERT INTO desarrolladores (nombre) VALUES (?)";
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
    public Desarrolladores leerDesarrollador(int id) throws SQLException {
        String sql = "SELECT * FROM desarrolladores WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Desarrolladores(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
            }
        }
        return null;
    }

    // Obtener todos los desarrolladores
    public List<Desarrolladores> obtenerTodos() throws SQLException {
        List<Desarrolladores> desarrolladores = new ArrayList<>();
        String sql = "SELECT * FROM desarrolladores";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                desarrolladores.add(new Desarrolladores(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }
        }
        return desarrolladores;
    }

    // Actualizar un desarrollador
    public void actualizarDesarrollador(Desarrolladores desarrollador) throws SQLException {
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


