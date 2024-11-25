package org.example;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class RegistroAñadir { // TODO: 11/03/2023 Añadir método para importar archivo CSV

    private static final Scanner sc = new Scanner(System.in);
    private static final Connection connection;

    static {
        try {
            connection = ConnectionDB.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al establecer la conexión a la base de datos", e);
        }
    }

    public static void menu() throws SQLException, IOException, InterruptedException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Leer Registros ===");
            System.out.println("1. Añadir registro por csv");
            System.out.println("2. Volver al menú principal");
            System.out.println("3. Salir del programa");
            int opcion = leerOpcion();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese la ruta completa del archivo CSV:");
                    String filePath = sc.nextLine(); // Escanea la entrada del usuario
                    File file = new File(filePath);
                    if (!file.exists() || !file.isFile()) {
                        System.err.println("El archivo no existe o no es válido: " + filePath);
                        break;
                    }
                    try {
                        CSVImporter.importarCSV(filePath);
                        System.out.println("Archivo CSV importado correctamente.");
                    } catch (IOException e) {
                        System.err.println("Error al leer el archivo CSV: " + e.getMessage());
                    } catch (SQLException e) {
                        System.err.println("Error al interactuar con la base de datos: " + e.getMessage());
                    }
                    break;


                case 2 :
                    System.out.println("Volviendo al menú principal...");
                    salir = true; // Cambia el comportamiento según lo que quieras lograr
                    break;
                case 3 :
                    System.out.println("Saliendo del programa...");
                    Thread.sleep(1000); // Simula una pausa al cerrar
                    System.exit(0);

                    break;
                default :
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }
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



