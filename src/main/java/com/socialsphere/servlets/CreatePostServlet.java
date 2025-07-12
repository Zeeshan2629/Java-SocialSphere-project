package com.socialsphere.servlets;

import com.socialsphere.utils.DBConnection;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class CreatePostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set content type to HTML to render links properly
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

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

                out.println("Post created! <a href='home.jsp'>Back to Home</a>");
            } else {
                out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error while creating post.");
        }
    }
}
