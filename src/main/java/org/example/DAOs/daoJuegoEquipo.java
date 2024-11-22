package org.example.DAOs;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class daoJuegoEquipo {
    private static Connection connection;

    public daoJuegoEquipo(Connection connection) {
        this.connection = connection;
    }



    // Leer una relación de juego_equipo por su ID
    public juego_equipo leerJuegoEquipo(int id) throws SQLException {
        String sql = "SELECT * FROM juego_equipo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new juego_equipo(
                        rs.getInt("id"),
                        rs.getInt("juego_id")
                );
            }
        }
        return null;
    }

    // Obtener todas las relaciones de juegos y equipos
    public List<juego_equipo> obtenerTodos() throws SQLException {
        List<juego_equipo> juegoEquipoList = new ArrayList<>();
        String sql = "SELECT * FROM juego_equipo";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                juegoEquipoList.add(new juego_equipo(
                        rs.getInt("id"),
                        rs.getInt("juego_id")
                ));
            }
        }
        return juegoEquipoList;
    }


    }

