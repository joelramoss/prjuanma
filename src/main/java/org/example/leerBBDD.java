package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class leerBBDD {
    private static final Scanner sc = new Scanner(System.in);

    public static void menu() throws InterruptedException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú Leer Registros ===");
            System.out.println("1. Leer un registro específico de la base de datos");
            System.out.println("2. Volver al menú principal");
            System.out.println("3. Salir del programa");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    leerRegistro();
                    break;

                case 2:
                    salir = true; // Salir del menú de lectura, y volver al menú principal
                    break;

                case 3:
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void leerRegistro() {
        System.out.println("\nSeleccione una opción de lectura:");
        System.out.println("1. Datos de un ID de juego aleatorio");
        System.out.println("2. Datos de un ID de juego específico");
        System.out.println("3. Datos de un equipo de desarrollo aleatorio");
        System.out.println("4. Datos de un equipo de desarrollo específico");
        System.out.println("5. Datos de un género aleatorio");
        System.out.println("6. Datos de un género específico");

        int opcion = leerOpcion();

        try {
            switch (opcion) {
                case 1:
                    System.out.println("Generando un ID de juego aleatorio...");
                    
                    break;
                case 2:
                    System.out.print("Ingrese el ID de juego: ");
                    int idJuego = sc.nextInt();
                    leerJuegoPorId(idJuego); // Llamada al método para leer el juego por ID
                    break;
                // Agregar más casos para las otras opciones según sea necesario

                default:
                    System.out.println("Opción no válida. Regresando al menú.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el registro: " + e.getMessage());
        }
    }

    // Método para leer un juego por ID desde la base de datos
    private static void leerJuegoPorId(int id) throws SQLException {
        String query = "SELECT * FROM juegos WHERE id = ?";
        try (Connection conn = ConnectionDB.getInstance("nombreBD", 3306, "usuario", "contraseña").getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Juego encontrado:");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Título: " + rs.getString("title"));
                System.out.println("Fecha de lanzamiento: " + rs.getDate("releaseDate"));
                System.out.println("Resumen: " + rs.getString("summary"));
            } else {
                System.out.println("No se encontró un juego con el ID proporcionado.");
            }
        }
    }

    // Método auxiliar para leer opciones con validación
    private static int leerOpcion() {
        try {
            return sc.nextInt();
        } catch (Exception e) {
            sc.nextLine(); // Limpiar buffer
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return -1;
        }
    }
}
