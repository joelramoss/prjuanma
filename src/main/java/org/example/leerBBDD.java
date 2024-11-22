package org.example;

import org.example.DAOs.Juego;

import java.sql.SQLOutput;
import java.util.Scanner;

public class leerBBDD {





    public static void menu() throws InterruptedException {
        boolean salir = false;

        Scanner sc = new Scanner(System.in);
        while (!salir) {
            System.out.println("Elige una opción:");
            System.out.println("1. Leer un registro específico de la base de datos");
            System.out.println("2. Volver al menú");
            System.out.println("3. Salir del programa");
            int opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("Datos de un ID de juego aleatorio");
                    System.out.println("Datos de un ID de juego especifico");
                    System.out.println("Datos de un equipo de desaroollo aleatorio");
                    System.out.println("Datos de un equipo de desarollo especifico");
                    System.out.println("Datos de un género aleatorio");
                    System.out.println("Datos de un género especifico");
                    

                    break;

                case 2:
                    System.out.println("Volver al menú...");
                    Main.menu();
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }

    }
}
