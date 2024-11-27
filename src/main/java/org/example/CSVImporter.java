package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.entidades.Generos;
import org.example.DAOs.daoGeneros;
import org.example.entidades.Desarolladores;
import org.example.DAOs.daoDesarrolladores;
import org.example.entidades.Juego;
import org.example.DAOs.daoJuego;
import org.example.DAOs.daoJuegosGenerados;
import org.example.DAOs.daoJuegoEquipo;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.example.CSVImporter.DateConverter.parseIntegerWithK;
import static org.example.CSVImporter.DateConverter.parseListFromString;

public class CSVImporter {

    private Connection connection;

    // Asumiendo que la conexión se pasa correctamente a este método
    public static void importarCSV(String filePath, Connection connection) throws IOException, SQLException {
        // Crea una instancia de daoGeneros usando la conexión proporcionada
        daoGeneros generosDAO = new daoGeneros(connection);

        // Lee el archivo CSV
        FileReader reader = new FileReader(filePath);

        // Configura el formato del CSV
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader() // Esto indica que la primera fila es el encabezado
                .parse(reader);

        // Procesa cada registro (fila) en el CSV
        for (CSVRecord record : records) {
            // Lee los datos de cada columna
            String id = record.get("ID");
            String title = record.get("Title");
            String releaseDate = record.get("Release Date");
            String team = record.get("Team");
            String rating = record.get("Rating");
            String timesListed = record.get("Times Listed");
            String numOfReviews = record.get("Number of Reviews");
            String genres = record.get("Genres");
            String summary = record.get("Summary");
            String reviews = record.get("Reviews");
            String plays = record.get("Plays");
            String playing = record.get("Playing");
            String backlogs = record.get("Backlogs");
            String wishlist = record.get("Wishlist");

            // Parseamos las columnas Team y Genres que están en formato lista (string)
            List<String> teamList = parseListFromString(team);
            List<String> genresList = parseListFromString(genres);

            // Crear el objeto Juego para insertar
            Juego juego = new Juego();
            juego.setId(Integer.parseInt(id));  // Asegúrate de que el ID sea un número entero
            juego.setTitle(title);

            // Conversión de fecha con múltiples formatos
            juego.setReleaseDate(DateConverter.convertToDate(releaseDate)); // Metodo actualizado para manejar diferentes formatos

            juego.setSummary(summary);
            juego.setPlays(parseIntegerWithK(plays)); // Usamos la función para convertir los valores con 'K'
            juego.setPlaying(parseIntegerWithK(playing));
            juego.setBacklogs(parseIntegerWithK(backlogs));
            juego.setWishlist(parseIntegerWithK(wishlist));

            // Inserta el juego en la base de datos
            daoJuego.crearJuego(juego);

            // Procesamos los géneros
            for (String genre : genresList) {
                Generos genero = new Generos();
                genero.setGeneros(genre);
                generosDAO.crearGenero(genero); // Ahora usamos la instancia de daoGeneros

                // Relacionamos el juego con el genero1
                daoJuegosGenerados juegosGeneradosDAO = new daoJuegosGenerados(connection);
                juegosGeneradosDAO.crearRelacionJuegoGenero(juego.getId(), genero.getId());
            }


            // Procesamos los equipos
            for (String developer : teamList) {
                Desarolladores desarrollador = new Desarolladores();
                desarrollador.setNombre(developer);
                daoDesarrolladores.crearDesarrollador(desarrollador); // Insertamos en la base de datos

                // Relacionamos el juego con el desarrollador
                daoJuegoEquipo.crearRelacionJuegoDesarrollador(juego.getId(), desarrollador.getId());
            }
        }
    }

    // Método para convertir una cadena de fecha a java.sql.Date con múltiples formatos
    public class DateConverter {
        public static java.sql.Date convertToDate(String dateStr) {
            if (dateStr == null || dateStr.trim().isEmpty()) {
                throw new IllegalArgumentException("El string de fecha no puede ser nulo o vacío.");
            }

            // Elimina puntos y recorta espacios
            dateStr = dateStr.trim().replace(".", "");

            // Capitaliza la primera letra del mes para manejar "feb", "dec", etc.
            dateStr = capitalizeMonth(dateStr);

            // Define patrones compatibles
            DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                    DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH), // Ej: Feb 25, 2022
                    DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH) // Ej: February 25, 2022
            };

            for (DateTimeFormatter formatter : formatters) {
                try {
                    // Intenta analizar la fecha con el formato actual
                    LocalDate parsedDate = LocalDate.parse(dateStr, formatter);
                    return java.sql.Date.valueOf(parsedDate);
                } catch (DateTimeParseException ignored) {
                    // Continúa al siguiente formato si falla
                }
            }

            // Lanza una excepción si ningún formato funcionó
            throw new IllegalArgumentException("Formato de fecha no válido: " + dateStr);
        }

        private static String capitalizeMonth(String dateStr) {
            // Separa los componentes (mes, día, año) y capitaliza el mes
            String[] parts = dateStr.split(" ", 3);
            if (parts.length < 3) return dateStr; // Si no hay al menos 3 partes, retornamos el original

            // Capitalizamos el mes y reconstruimos la cadena
            parts[0] = parts[0].substring(0, 1).toUpperCase(Locale.ENGLISH) + parts[0].substring(1).toLowerCase(Locale.ENGLISH);
            return String.join(" ", parts);
        }

        // Método para parsear una cadena que contiene una lista en formato JSON o similar
        public static List<String> parseListFromString(String input) {
            input = input.replaceAll("[\\[\\]'\"]+", "").trim();  // Limpiar corchetes, comillas y espacios
            String[] items = input.split(",");  // Separar por comas
            List<String> list = new ArrayList<>();
            for (String item : items) {
                list.add(item.trim());  // Eliminar espacios adicionales y añadir a la lista
            }
            return list;
        }

        // Método para convertir los valores con 'K' a números enteros
        public static int parseIntegerWithK(String value) {
            if (value.endsWith("K")) {
                return (int) (Double.parseDouble(value.replace("K", "").trim()) * 1000);
            } else {
                return Integer.parseInt(value.trim());
            }
        }
    }
}
