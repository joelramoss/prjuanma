package org.example;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in); // Instancia única de Scanner

    public static void main(String[] args) throws SQLException {
        try {
            menu(); // Inicia el menú principal
        } catch (InterruptedException IE) {
            System.out.println("Se produjo un error: " + IE.getMessage());
        } finally {
            sc.close(); // Cierra el Scanner al finalizar
        }
    }

    public static void menu() throws InterruptedException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Conectar a la base de datos");
            System.out.println("2. Crear un Registro");
            System.out.println("3. Leer Registros");
            System.out.println("4. Actualizar Registros");
            System.out.println("5. Eliminar Registros");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1 :
                    nivel0();
                    break;
                case 2 :
                    System.out.println("Funcionalidad Crear Registro (pendiente de implementación).");
                break;
                case 3 :
                    System.out.println("Funcionalidad Leer Registros (pendiente de implementación).");
                    break;
                case 4 :
                    System.out.println("Funcionalidad Actualizar Registros (pendiente de implementación).");
                break;
                case 5 :
                    System.out.println("Funcionalidad Eliminar Registros (pendiente de implementación).");
                break;
                case 6 :
                    System.out.println("Cerrando aplicación...");
                    Thread.sleep(1000); // Simula una pausa al cerrar
                    salir = true;
                break;
                default :
                    System.out.println("Opción no válida. Intente de nuevo.");
                break;
            }
        }
    }

    public static void nivel0() {
        try {
            System.out.print("\nIngrese el puerto de la base de datos: ");
            int puerto = leerOpcion();
            System.out.print("Ingrese el nombre de la base de datos: ");
            String nombreBD = sc.nextLine();

            System.out.print("Ingrese el nombre de usuario: ");
            String username = sc.nextLine();

            System.out.print("Ingrese la contraseña: ");
            String password = sc.nextLine();

            // Establecer conexión
            ConnectionDB connection = ConnectionDB.getInstance(nombreBD, puerto, username, password);
            System.out.println("Conexión exitosa a la base de datos: " + nombreBD);

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Método auxiliar para leer opciones y validar entradas numéricas
    private static int leerOpcion() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine(); // Limpiar buffer
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return -1; // Retorna un valor inválido para manejarlo en el menú
        }
    }
}
