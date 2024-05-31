package org.example.oop;

import org.example.oop.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.connect("database.db");
        String createSQLTable = "CREATE TABLE IF NOT EXISTS accounts( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL)";

        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(createSQLTable);
            statement.executeUpdate();
            System.out.println("table accounts created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.disconnect();
    }
}