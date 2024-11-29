package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.entidades.*;
import org.example.DAOs.daoGeneros;
import org.example.DAOs.daoDesarrolladores;
import org.example.DAOs.daoJuego;
import org.example.DAOs.daoJuegosGenerados;
import org.example.DAOs.daoJuegoEquipo;
import org.example.DAOs.daoRating;
import org.example.DAOs.daoDetallesJuego;  // Importar el DAO de DetallesJuego

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CSVImporter {

    // La conexión se pasa al método, no es necesario tenerla como variable de instancia
    public static void importarCSV(String filePath, Connection connection) throws IOException, SQLException {
        // Usamos la conexión directamente al crear los DAOs
        daoGeneros generosDAO = new daoGeneros(connection);
        daoJuego daoJuego = new daoJuego(); // Se pasa la conexión al constructor
        daoDesarrolladores daoDesarrolladores = new daoDesarrolladores(connection);
        daoJuegosGenerados juegosGeneradosDAO = new daoJuegosGenerados(connection);
        daoJuegoEquipo daoJuegoEquipo = new daoJuegoEquipo(connection);
        daoDetallesJuego detallesJuegoDAO = new daoDetallesJuego(connection); // Crear el DAO de DetallesJuego

        // Lee el archivo CSV
        FileReader reader = new FileReader(filePath);

        // Configura el formato del CSV
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader() // Esto indica que la primera fila es el encabezado
                .parse(reader);

        // Procesa cada registro (fila) en el CSV
        for (CSVRecord record : records) {
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
            try {
                juego.setReleaseDate(DateConverter.convertToDate(releaseDate)); // Metodo actualizado para manejar diferentes formatos
            } catch (IllegalArgumentException e) {
                System.out.println("Error al convertir la fecha para el juego con ID " + id + ": " + e.getMessage());
                continue; // Si la fecha no es válida, saltamos este registro
            }

            juego.setSummary(summary);
            juego.setPlays(parseIntegerWithK(plays)); // Usamos la función para convertir los valores con 'K'
            juego.setPlaying(parseIntegerWithK(playing));
            juego.setBacklogs(parseIntegerWithK(backlogs));
            juego.setWishlist(parseIntegerWithK(wishlist));

            // Inserta el juego en la base de datos
            daoJuego.crearJuego(juego);

// Usamos un HashMap para evitar duplicados. La clave es el género, y el valor es simplemente "true" para indicar que ya fue procesado.
            Map<String, Boolean> generosProcesadosMap = new HashMap<>();

            for (String genre : genresList) {
                // Verificamos si el género ya ha sido procesado
                if (!generosProcesadosMap.containsKey(genre)) {
                    // Si no ha sido procesado, lo agregamos al HashMap
                    generosProcesadosMap.put(genre, true);

                    // Creamos y guardamos el género en la base de datos
                    Generos genero = new Generos();
                    genero.setGeneros(genre);
                    generosDAO.crearGenero(genero); // Ahora usamos la instancia de daoGeneros

                    // Relacionamos el juego con el género
                    juegosGeneradosDAO.crearRelacionJuegoGenero(juego.getId(), genero.getId());
                }
            }


            // Procesamos los equipos
            for (String developer : teamList) {
                Desarrolladores desarrollador = new Desarrolladores();
                desarrollador.setNombre(developer);
                daoDesarrolladores.crearDesarrollador(desarrollador); // Insertamos en la base de datos, generando el ID

                // Relacionamos el juego con el desarrollador
                daoJuegoEquipo.crearRelacionJuegoDesarrollador(juego.getId(), desarrollador.getId());
            }

            // Procesar el rating
            daoRating ratingDAO = new daoRating(connection);
            Rating ratingObj = new Rating();
            ratingObj.setJuegoId(juego.getId()); // Relaciona el rating con el juego
            try {
                // Verifica si el rating es nulo o está vacío
                if (rating == null || rating.trim().isEmpty()) {
                    ratingObj.setRating(0.0); // Asigna un 0 si el rating es nulo o vacío
                } else {
                    ratingObj.setRating(Double.parseDouble(rating.trim())); // Convierte el rating a un número decimal
                }

                // Para el número de reseñas, usa parseIntegerWithK si tiene formato con "K"
                ratingObj.setNumberOfReviews(parseIntegerWithK(numOfReviews.trim())); // Convierte el número de reseñas a entero, considerando "K"

                ratingDAO.crearRating(ratingObj); // Inserta el rating en la base de datos
            } catch (NumberFormatException e) {
                System.out.println("Error en los datos de rating o reviews para el juego con ID " + id + ": " + e.getMessage());
            }

            // Ahora procesamos los detalles del juego (reseñas)
            if (reviews != null && !reviews.trim().isEmpty()) {
                // Creamos una nueva instancia de DetallesJuego
                DetallesJuego detallesJuego = new DetallesJuego(juego.getId(), reviews);
                detallesJuegoDAO.crearDetallesJuego(detallesJuego); // Inserta los detalles del juego
            }
        }
    }

    // Métodos de conversión de fechas y otros
    public static class DateConverter {
        public static java.sql.Date convertToDate(String dateStr) {
            if (dateStr == null || dateStr.trim().isEmpty()) {
                throw new IllegalArgumentException("El string de fecha no puede ser nulo o vacío.");
            }

            dateStr = dateStr.trim().replace(".", ""); // Elimina puntos y recorta espacios
            dateStr = capitalizeMonth(dateStr);

            DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                    DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH),
                    DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)
            };

            for (DateTimeFormatter formatter : formatters) {
                try {
                    LocalDate parsedDate = LocalDate.parse(dateStr, formatter);
                    return java.sql.Date.valueOf(parsedDate);
                } catch (DateTimeParseException ignored) {
                }
            }

            throw new IllegalArgumentException("Formato de fecha no válido: " + dateStr);
        }
    }

    private static String capitalizeMonth(String dateStr) {
        String[] parts = dateStr.split(" ", 3);
        if (parts.length < 3) return dateStr;
        parts[0] = parts[0].substring(0, 1).toUpperCase(Locale.ENGLISH) + parts[0].substring(1).toLowerCase(Locale.ENGLISH);
        return String.join(" ", parts);
    }

    public static List<String> parseListFromString(String input) {
        input = input.replaceAll("[\\[\\]'\"]+", "").trim();
        String[] items = input.split(",");
        List<String> list = new ArrayList<>();
        for (String item : items) {
            list.add(item.trim());
        }
        return list;
    }

    public static int parseIntegerWithK(String value) {
        if (value.endsWith("K")) {
            return (int) (Double.parseDouble(value.replace("K", "").trim()) * 1000);
        } else {
            return Integer.parseInt(value.trim());
        }
    }
}
