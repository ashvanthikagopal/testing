package service;

import java.sql.*;

public class DbViewer {

    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:file:./data/intellitrace-db;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";
            try (Connection connection = DriverManager.getConnection(url, "sa", "")) {
                try (Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery("SELECT id, username, password FROM users");
                    System.out.println("ID | USERNAME | PASSWORD");
                    System.out.println("----------------------");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getLong("id") + " | " + resultSet.getString("username") + " | " + resultSet.getString("password"));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to view database: " + e.getMessage());
        }
    }
}
