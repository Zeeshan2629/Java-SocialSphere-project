package com.socialsphere.servlets;

import com.socialsphere.utils.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Set session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("home.jsp");
            } else {
                response.getWriter().println("Invalid credentials. <a href='login.html'>Try again</a>");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Logs full stack trace
            response.getWriter().println("Error: " + e.getMessage()); // Display in browser
        }

    }
}
