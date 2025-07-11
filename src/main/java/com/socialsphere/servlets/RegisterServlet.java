package com.socialsphere.servlets;

import com.socialsphere.dao.UserDAO;
import com.socialsphere.dao.UserDAOImpl;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set response content type to HTML so that <a> links work
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserDAO userDAO = new UserDAOImpl();

        try {
            if (userDAO.isUsernameTaken(username)) {
                out.println("Username already exists. <a href='register.html'>Try again</a>");
                return;
            }

            if (userDAO.registerUser(username, password, email)) {
                out.println("Registration successful. <a href='login.html'>Login</a>");
            } else {
                out.println("Registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error occurred. Please try again.");
        }
    }
}
