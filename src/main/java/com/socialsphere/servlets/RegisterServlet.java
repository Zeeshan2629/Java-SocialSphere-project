package com.socialsphere.servlets;

import com.socialsphere.utils.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        try (Connection conn = DBConnection.getConnection()) {
            // Check if username exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                response.getWriter().println("Username already exists. <a href='register.html'>Try again</a>");
                return;
            }

            // Insert new user
            PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO users (username, password, email) VALUES (?, ?, ?)"
            );
            insertStmt.setString(1, username);
            insertStmt.setString(2, password); // For production, hash this.
            insertStmt.setString(3, email);
            insertStmt.executeUpdate();

            response.getWriter().println("Registration successful. <a href='login.html'>Login</a>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred. Please try again.");
        }
    }
}
