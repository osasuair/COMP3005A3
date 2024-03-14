package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    /**
     * Get a connection to the database.
     * @return a connection to the database
     */
    public static Connection getConnection() {
        // JDBC & Database credentials
        String url = "jdbc:postgresql://localhost:5432/test_db";
        String user = "postgres";
        String password = "root";
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");  // Load the PostgreSQL driver
            // Connect to the database
            conn = DriverManager.getConnection(url, user, password);  // Establish the connection
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to establish connection.");
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Close the connection to the database.
     * @param conn the connection to the database
     */
    public static void closeConnection(Connection conn) {
        try {
            conn.close();  // Close the connection
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}