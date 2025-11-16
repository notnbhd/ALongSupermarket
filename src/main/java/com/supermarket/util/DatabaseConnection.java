package com.supermarket.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class
 * Provides connection to MySQL database
 */
public class DatabaseConnection {
    
    // Database credentials - modify these according to your setup
    private static final String URL = "jdbc:mysql://localhost:3306/supermarket_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234"; // Change this to your MySQL password
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Load the JDBC driver
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to create database connection!");
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Close database connection
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close database connection!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed!");
            e.printStackTrace();
            return false;
        }
    }
}
