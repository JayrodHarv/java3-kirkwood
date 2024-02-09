package edu.kirkwood.learnx.data;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() {
        Dotenv dotenv = Dotenv.load();
        String db_driver = dotenv.get("DB_FULL_DRIVER");
        String connectionString = dotenv.get("DB_CONNECTION_LEARNX");
        String username = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        try {
            Class.forName(db_driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);
            if(connection.isValid(2)) {
                System.out.println("Connection successful");
                return connection;
            }
        } catch (SQLException e) {
            System.out.println("Connection string, username, or password is incorrect.");
        }
        return null;
    }
}
