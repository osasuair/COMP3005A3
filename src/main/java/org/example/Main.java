package org.example;

import java.sql.*;

/**
 * Main class for managing students in a database.
 */
public class Main {
    // Get a connection to the database
    static Connection conn = JDBCConnection.getConnection();

    /**
     * Main method for the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Students in the database:");
        getAllStudents();  // Get all students from the database

        System.out.println("\nAdding a student to the database:");
        addStudent("John", "Doe", "testEmail@mail.ca", Date.valueOf("2021-01-01"));  // Add a student to the database
        getAllStudents();  // Get all students from the database

        System.out.println("\nUpdating a student's email in the database:");
        updateStudentEmail(1, "theWestCost@gmail.ca");  // Update a student's email in the database
        getAllStudents();

        System.out.println("\nDeleting a student from the database:");
        deleteStudent(1);  // Delete a student from the database
        getAllStudents();  // Get all students from the database

        JDBCConnection.closeConnection(conn);  // Close the connection to the database
    }

    /**
     * Retrieves all students from the database and prints their details.
     */
    public static void getAllStudents() {
        try {
            // Create a statement
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + " " + rs.getString("first_name") + " " +
                                   rs.getString("last_name") + " " + rs.getString("email") + " " +
                                   rs.getDate("enrollment_date"));
            }
        }
        catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Adds a new student to the database.
     *
     * @param firstName      the first name of the student
     * @param lastName       the last name of the student
     * @param email          the email of the student
     * @param enrollmentDate the enrollment date of the student
     */
    public static void addStudent(String firstName, String lastName, String email, Date enrollmentDate) {
        try {
            String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setDate(4, enrollmentDate);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Updates the email of a student in the database.
     * @param studentId the id of the student
     * @param newEmail the new email of the student
     */
    public static void updateStudentEmail(int studentId, String newEmail) {
        try {
            String sql = "UPDATE students SET email = ? WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Deletes a student from the database.
     * @param studentId the id of the student to be deleted
     */
    public static void deleteStudent(int studentId) {
        try {
            String sql = "DELETE FROM students WHERE student_id = " + studentId;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}