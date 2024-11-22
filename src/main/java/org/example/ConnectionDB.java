package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static ConnectionDB instance; // Instancia única de ConnectionDB
    private Connection connection;
    private String url;

    private ConnectionDB() throws SQLException {
        this.url = "jdbc:mysql://localhost:3306/games";
        this.connection = DriverManager.getConnection(url, "root", "");
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConnectionDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionDB();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConnectionDB();
        }

        return instance;
    }
}
