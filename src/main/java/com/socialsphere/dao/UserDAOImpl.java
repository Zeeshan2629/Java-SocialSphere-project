package com.socialsphere.dao;

import com.socialsphere.utils.DBConnection;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    public boolean isUsernameTaken(String username) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public boolean registerUser(String username, String password, String email) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (username, password, email) VALUES (?, ?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, password); // consider hashing in future
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean validateUser(String username, String password) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public int getUserId(String username) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("User not found");
            }
        }
    }
}
