package com.socialsphere.servlets;

import com.socialsphere.utils.DBConnection;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.sql.*;

public class CreatePostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.html");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Get user ID from username
            PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            userStmt.setString(1, username);
            ResultSet rs = userStmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");

                // Insert post
                PreparedStatement postStmt = conn.prepareStatement(
                        "INSERT INTO posts (user_id, title, content) VALUES (?, ?, ?)"
                );
                postStmt.setInt(1, userId);
                postStmt.setString(2, title);
                postStmt.setString(3, content);
                postStmt.executeUpdate();

                response.getWriter().println("Post created! <a href='home.jsp'>Back to Home</a>");
            } else {
                response.getWriter().println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error while creating post.");
        }
    }
}
