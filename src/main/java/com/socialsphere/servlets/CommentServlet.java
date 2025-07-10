package com.socialsphere.servlets;

import com.socialsphere.utils.DBConnection;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.*;
import java.sql.*;

public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = request.getParameter("commentContent");
        int postId = Integer.parseInt(request.getParameter("postId"));
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.html");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                int userId = userRs.getInt("id");

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)"
                );
                stmt.setInt(1, postId);
                stmt.setInt(2, userId);
                stmt.setString(3, content);
                stmt.executeUpdate();

                response.sendRedirect("viewPosts.jsp");
            } else {
                response.getWriter().println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error while adding comment.");
        }
    }
}
