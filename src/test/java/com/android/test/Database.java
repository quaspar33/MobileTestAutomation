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
            jsonNode = objectMapper.readTree(new File("cred.json"));
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

    public void query(String query) {
        if (connection == null) {
            System.err.println("Połączenie nie zostało nawiązane. Użyj connect() przed wykonywaniem zapytań.");
            return;
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Nazwa: " + resultSet.getString("nazwa"));
            }

        } catch (SQLException e) {
            System.err.println("Błąd podczas wykonywania zapytania: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
