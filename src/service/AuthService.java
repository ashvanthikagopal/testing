package service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import util.Validation;

public class AuthService {

    private final String jdbcUrl;
    
    // Static initializer to load H2 driver once
    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load H2 driver: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public AuthService(String databasePath) {
        Path resolvedPath = Paths.get(databasePath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(resolvedPath.getParent());
        } catch (Exception ignored) {
        }
        this.jdbcUrl = "jdbc:h2:file:" + resolvedPath.toString().replace('\\', '/') + ";DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";
        initializeDatabase();
    }

    public boolean registerUser(String name, String email, String mobileNo, String password, String confirmPassword) {
        // Validate all inputs are provided
        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty() ||
                mobileNo == null || mobileNo.trim().isEmpty() || password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
            System.out.println("All fields are required.");
            return false;
        }
        
        String trimmedName = name.trim();
        String trimmedEmail = email.trim();
        String trimmedMobileNo = mobileNo.trim();
        
        // Validate name
        if (!Validation.isValidName(trimmedName)) {
            return false;
        }
        
        // Validate email format
        if (!Validation.isValidEmail(trimmedEmail)) {
            return false;
        }
        
        // Validate mobile number
        if (!Validation.isValidMobileNumber(trimmedMobileNo)) {
            return false;
        }
        
        // Validate password strength
        if (!Validation.isValidPassword(password)) {
            return false;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return false;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "")) {
            // Check if email already exists
            try (PreparedStatement checkStmt = connection.prepareStatement(
                    "SELECT 1 FROM users WHERE email = ?")) {
                checkStmt.setString(1, trimmedEmail);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Email already registered.");
                        return false;
                    }
                }
            }

            // Insert new user
            try (PreparedStatement insertStmt = connection.prepareStatement(
                    "INSERT INTO users (name, email, mobile_no, password) VALUES (?, ?, ?, ?)")) {
                insertStmt.setString(1, trimmedName);
                insertStmt.setString(2, trimmedEmail);
                insertStmt.setString(3, trimmedMobileNo);
                insertStmt.setString(4, password);
                insertStmt.executeUpdate();
            }

            System.out.println("Registration successful.");
            return true;
        } catch (SQLException e) {
            System.out.println("Database error during registration: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Email and password are required.");
            return false;
        }
        
        String trimmedEmail = email.trim();
        
        // Validate email format
        if (!Validation.isValidEmail(trimmedEmail)) {
            return false;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "")) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT password FROM users WHERE email = ?")) {
                statement.setString(1, trimmedEmail);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("password");
                        if (storedPassword != null && storedPassword.equals(password)) {
                            System.out.println("Login successful.");
                            return true;
                        }
                    }
                }
            }

            System.out.println("Invalid email or password.");
            return false;
        } catch (SQLException e) {
            System.out.println("Database error during login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void initializeDatabase() {
        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "")) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(
                            "CREATE TABLE IF NOT EXISTS users (" +
                                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                                    "name VARCHAR(100) NOT NULL, " +
                                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                                    "mobile_no VARCHAR(10) NOT NULL, " +
                                    "password VARCHAR(255) NOT NULL)"
                    );
                    System.out.println("Database initialized successfully at: " + jdbcUrl);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database initialization error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}