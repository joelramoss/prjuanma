package org.example;
import org.example.DAOs.daoJuego;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class eliminarRegistros {
    private static final Scanner sc = new Scanner(System.in); // Instancia única de Scanner

    public static void menu() throws SQLException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú Eliminar Registros ===");
            System.out.println("1. Eliminar un juego por ID");
            System.out.println("2. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    eliminarJuego();
                    break;
                case 2:
                    System.out.println("Volviendo al Menú Principal...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    private static void eliminarJuego() {
        try {
            System.out.print("Ingrese el ID del juego a eliminar: ");
            int id = sc.nextInt();

            // Llamar al metodo eliminarJuego del DAO
            daoJuego.eliminarJuegoYRelaciones(id);

            System.out.println("El juego con ID " + id + " ha sido eliminado exitosamente.");
        } catch (InputMismatchException e) {
            sc.nextLine(); // Limpiar el buffer del Scanner
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el juego: " + e.getMessage());
        }
    }

    private static int leerOpcion() {
        try {
            return sc.nextInt();
        } catch (Exception e) {
            sc.nextLine(); // Limpiar buffer
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return -1; // Valor inválido para manejar errores
        }
    }
}
