package org.example.oop.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.oop.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    private DatabaseConnection databaseConnection;

    public AccountManager(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void init() {
        String createSQLTable = "CREATE TABLE IF NOT EXISTS accounts( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL)";

        try {
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(createSQLTable);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int register(String username, String password) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        String insertSQL = "INSERT INTO accounts (username, password) VALUES (?, ?)";
        Connection connection = databaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

public boolean authenticate(String username, String password) {
    String query = "SELECT * FROM accounts WHERE username=?";
    try {
        PreparedStatement statement = databaseConnection.getConnection().prepareStatement(query);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String hashedPassword = resultSet.getString("password");
            boolean check = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;
            if (check) {
                return true;
            } else {
                throw new AuthenticationException("Wrong password");
            }
        } else {
            throw new AuthenticationException("No such user");
        }
    } catch (SQLException | AuthenticationException e) {
        e.printStackTrace();
    }
    return false;
}


    public Account getAccount(String username) throws SQLException {
        int id;
        String accountUsername;
        String query = "SELECT * FROM accounts WHERE username=?";
        PreparedStatement statement = databaseConnection.getConnection().prepareStatement(query);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
            accountUsername = resultSet.getString(2);
            return new Account(id, accountUsername);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "AccountManager{" +
                "databaseConnection=" + databaseConnection +
                '}';
    }
}
