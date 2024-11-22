package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static ConnectionDB instance;
    private Connection connection;
    private String url;

    private ConnectionDB(String nombreBD, int puerto, String username, String password) throws SQLException {
        this.url = "jdbc:mysql://localhost:" + puerto + "/" + nombreBD;
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConnectionDB getInstance(String nombreBD, int puerto, String username, String password) throws SQLException {
        if (instance == null) {
            instance = new ConnectionDB(nombreBD, puerto, username, password);
        } else if (instance.getConnection().isClosed()) {
            instance = new ConnectionDB(nombreBD, puerto, username, password);
        }

        return instance;
    }
}
