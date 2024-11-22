package org.example;

import org.example.DAOs.Desarolladores;
import org.example.DAOs.Juego;
import org.example.DAOs.daoJuego;
import java.sql.*;
import java.util.Scanner;


public class leerBBDD {
    private static final Scanner sc = new Scanner(System.in);
    private static final Connection connection;
    private static final daoJuego juegoDAO;

    static {
        try {
            connection = ConnectionDB.getInstance().getConnection();
            juegoDAO = new daoJuego(connection); // Inicializa el DAO después de que la conexión esté lista
        } catch (SQLException e) {
            throw new RuntimeException("Error al establecer la conexión a la base de datos", e);
        }
    }


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
                    int idAleatorio = generarIDAleatorio(connection);
                    Juego juego1 = juegoDAO.leerJuego(idAleatorio);
                    if (idAleatorio != -1) {
                        juego1.toString();
                    } else {
                        System.out.println("No se encontró ningún juego.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el ID de juego: ");
                    int idJuego = sc.nextInt();
                    // Llamamos al metodo para leer el juego por su ID
                    Juego juego = juegoDAO.leerJuego(idJuego);

                    if (juego != null) {
                        System.out.println("Juego encontrado: " + juego.toString());
                    } else {
                        System.out.println("No se encontró el juego con el ID " + idJuego);
                    }
                    break;
                case 3:
                    System.out.println("Generando un ID aleatorio para un equipo...");

                    // Generamos el ID aleatorio del juego
                    int idAleatorioJuego = generarIDAleatorio(connection);

                    if (idAleatorioJuego != -1) {
                        // Llamamos al DAO para obtener el juego con ese ID aleatorio
                        Juego juego2 = juegoDAO.leerJuego(idAleatorioJuego);

                        // Verificamos si se encontró el juego
                        if (juego2 != null) {
                            System.out.println("Juego encontrado: " + juego2.toString());

                            // Ahora que tenemos el juego, generamos el ID aleatorio para el equipo
                            int idAleatorioEquipo = generarIDAleatorio(connection);

                            // Suponiendo que tenemos una clase para los desarrolladores, la usamos
                            Desarolladores desarrollador = new Desarolladores(idAleatorioEquipo, juego2.getId());

                            System.out.println("Equipo que creó el juego: " + desarrollador.toString());
                        } else {
                            System.out.println("No se encontró el juego con el ID aleatorio " + idAleatorioJuego);
                        }
                    } else {
                        System.out.println("No se pudo generar un ID aleatorio para un juego.");
                    }
                    break;

                default:
                    System.out.println("Opción no válida. Regresando al menú.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el registro: " + e.getMessage());
        }

    }





    public static int generarIDAleatorio(Connection connection) throws SQLException {
        String sql = "SELECT id FROM juego ORDER BY RAND() LIMIT 1"; // Consulta para seleccionar un ID aleatorio
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("id"); // Devuelve el ID del juego aleatorio
            }
        }
        return -1; // Si no se encuentra ningún juego
    }

    // Metodo auxiliar para leer opciones con validación
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
