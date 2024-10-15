package com.android.test;

import java.sql.*;

public class Database {
    private Connection connection;
    private JsonHandler jsonHandler;

    public void connect() {
        jsonHandler = new JsonHandler("src/test/java/com/android/test/cred.json");
        String jdbcUrl = jsonHandler.getStrFromJson("jdbcUrl");
        String username = jsonHandler.getStrFromJson("username");
        String password = jsonHandler.getStrFromJson("password");

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Połączenie z bazą danych nawiązane!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Połączenie z bazą danych zamknięte.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String queryForLogin(String query) {
        StringBuilder output = new StringBuilder();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");

                output.append("id: ").append(id).append(", login: ").append(login).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public String queryForTempPassword(String query) {
        StringBuilder output = new StringBuilder();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String password = resultSet.getString("text");
                String sendDate = resultSet.getString("sendDate");
                String number = resultSet.getString("number");

                output.append(password).append(";").append(sendDate).append(";").append(number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public int executeUpdate(String query) {
        int rowsAffected = 0;

        try (Statement statement = connection.createStatement()) {
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsAffected;
    }
}
