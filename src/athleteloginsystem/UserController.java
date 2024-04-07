/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package athleteloginsystem;
import java.sql.*;
/**
 *
 * @author Administrator
 */
public class UserController {
        private static final String JDBC_URL = "jdbc:mysql://localhost:3306/athlete";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    private static UserController instance;

    private UserController() {
        // Private constructor to prevent instantiation from outside
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public boolean registerUser(String username, String password, String fullName, String address, String medicalHistory, String course, String sports) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String sql = "INSERT INTO athlete (username, password, full_name, address, medical_history, course, sports) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, fullName);
                stmt.setString(4, address);
                stmt.setString(5, medicalHistory);
                stmt.setString(6, course);
                stmt.setString(7, sports);

                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM athlete WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // If there's a match, the user exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
