package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        menu();
    }
    public static void menu () throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("Bienvenido a la aplicación de gestión de registros");
            System.out.println("Seleccione una opción:");
            System.out.println("0. Conectar a la base de datos");
            System.out.println("1. Crear un Registro para la base de datos");
            System.out.println("2. Leer Registros de la base de datos");
            System.out.println("3. Actualizar Registros de la base de datos");
            System.out.println("4. Eliminar Registros de la base de datos");
            System.out.println("5. Salir");

            int opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 0:
                    System.out.print("Ingrese el puerto de la base de datos: ");
                    int puerto = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Ingrese el nombre de la base de datos: ");
                    String nombreBD = sc.nextLine();

                    System.out.print("Ingrese el nombre de usuario: ");
                    String username = sc.nextLine();

                    System.out.print("Ingrese la contraseña: ");
                    String password = sc.nextLine();

                    try {
                        ConnectionDB.getInstance(nombreBD, puerto, username, password);
                        System.out.println("Conexión establecida correctamente a la base de datos: " + nombreBD);
                    } catch (SQLException e) {
                        System.out.println("Error al conectar con la base de datos: " + e.getMessage());
                    }
                    break;

                case 1:

                    break;

                case 2:
                    leerBBDD.menu();
                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 5:
                    Thread.sleep(1000);
                    System.out.println("Cerrando aplicación...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}
