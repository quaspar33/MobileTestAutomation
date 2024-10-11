package com.android.test;

import java.io.File;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection = null;

    public void connect() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(new File("src/test/java/com/android/test/cred.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert jsonNode != null;
        String jdbcUrl = jsonNode.get("jdbcUrl").asText();
        String username = jsonNode.get("username").asText();
        String password = jsonNode.get("password").asText();

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Połączenie z bazą danych nawiązane!");
        } catch (SQLException e) {
            System.err.println("Błąd podczas łączenia z bazą danych: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Połączenie z bazą danych zamknięte.");
            } catch (SQLException e) {
                System.err.println("Błąd podczas zamykania połączenia: " + e.getMessage());
            }
        }
    }

    public String queryForLogin(String query) {
        if (connection == null) {
            System.err.println("Połączenie nie zostało nawiązane. Użyj connect() przed wykonywaniem zapytań.");
            return null;
        }

        StringBuilder output = new StringBuilder();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");

                output.append("ID: ").append(id).append(", Login: ").append(login).append("\n");
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas wykonywania zapytania: " + e.getMessage());
            e.printStackTrace();
        }

        return output.toString();
    }

    public ResultSet queryForTempPassword(String query) {
        if (connection == null) {
            System.err.println("Połączenie nie zostało nawiązane. Użyj connect() przed wykonywaniem zapytań.");
            return null;
        }

        ResultSet outputResultSet = null;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            outputResultSet = resultSet;
        } catch (SQLException e) {
            System.err.println("Błąd podczas wykonywania zapytania: " + e.getMessage());
            e.printStackTrace();
        }

        return outputResultSet;
    }

    public int executeUpdate(String query) {
        if (connection == null) {
            System.err.println("Połączenie nie zostało nawiązane. Użyj connect() przed wykonywaniem zapytań.");
            return -1;
        }

        int rowsAffected = 0;

        try (Statement statement = connection.createStatement()) {
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Błąd podczas wykonywania zapytania modyfikującego dane: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected;
    }
}
