package org.example;

import org.example.DAOs.daoJuegoEquipo;
import org.example.entidades.Juego;
import org.example.DAOs.daoJuego;
import org.example.entidades.juego_equipo;
import org.example.entidades.JuegosGenerados;
import org.example.DAOs.daoJuegosGenerados;
import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class leerBBDD {
    private static final Scanner sc = new Scanner(System.in);
    private static final Connection connection;
    private static final daoJuego juegoDAO;

    static {
        try {
            connection = ConnectionDB.getInstance().getConnection();
            juegoDAO = new daoJuego(); // Inicializa el DAO después de que la conexión esté lista
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
                    Thread.sleep(1000); // Simula una pausa al cerrar
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void leerRegistro() {
        System.out.println("\nSeleccione una opción de lectura:");
        System.out.println("1. Datos de un ID de juego específico");
        System.out.println("2. Datos de un equipo de desarrollo específico");
        System.out.println("3. Datos de un género específico");

        int opcion = leerOpcion();

        try {
            switch (opcion) {
                case 1:
                    extracted1();
                    break;

                case 2:
                    extracted();
                    break;

                case 3:
                    extracted2();
                    break;

                default:
                    System.out.println("Opción no válida. Regresando al menú.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el registro: " + e.getMessage());
        }
    }

    private static void extracted2() throws SQLException {
        System.out.println("Ingrese el género de los juegos: ");
        String nombreGenero = sc.nextLine();

        // Instancia del DAO
        daoJuegosGenerados juegosGeneradosDAO = new daoJuegosGenerados(connection);

        // Llamar al método para obtener los juegos asociados al género
        List<JuegosGenerados> juegosPorGenero = juegosGeneradosDAO.obtenerJuegosPorGenero(nombreGenero);

        // Mostrar los resultados
        if (juegosPorGenero.isEmpty()) {
            System.out.println("No se encontraron juegos para el género: " + nombreGenero);
        } else {
            System.out.println("Juegos asociados al género " + nombreGenero + ":");
            for (JuegosGenerados a : juegosPorGenero) {
                System.out.println(a.toString());
            }
        }
    }

    private static void extracted1() throws SQLException {
        System.out.print("Ingrese el ID de juego: ");
        int idJuego = sc.nextInt();
        sc.nextLine(); // Limpia el buffer

        // Llamamos al método para leer el juego por su ID
        Juego juego = juegoDAO.obtenerPorId(idJuego);

        if (juego != null) {
            System.out.println("Juego encontrado: " + juego.toString());
        } else {
            System.out.println("No se encontró el juego con el ID " + idJuego);
        }
    }

    private static void extracted() throws SQLException {
        System.out.println("Ingrese el nombre del equipo de desarrollo: ");
        String nombreEquipo = sc.nextLine();

        // Instancia del DAO
        daoJuegoEquipo juegoEquipoDAO = new daoJuegoEquipo(connection);

        // Llamar al método para obtener los juegos relacionados con el nombre del equipo
        List<juego_equipo> listaJuegosEquipo = juegoEquipoDAO.obtenerPorNombreEquipo(nombreEquipo);

        // Mostrar los resultados
        if (listaJuegosEquipo.isEmpty()) {
            System.out.println("No se encontraron juegos para el equipo con nombre: " + nombreEquipo);
        } else {
            System.out.println("Juegos relacionados con el equipo " + nombreEquipo + ":");
            for (juego_equipo je : listaJuegosEquipo) {
                System.out.println(je.toString());
            }
        }
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
