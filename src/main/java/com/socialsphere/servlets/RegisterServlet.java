package com.socialsphere.servlets;

import com.socialsphere.dao.UserDAO;
import com.socialsphere.dao.UserDAOImpl;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserDAO userDAO = new UserDAOImpl();

        try {
            if (userDAO.isUsernameTaken(username)) {
                response.getWriter().println("Username already exists. <a href='register.html'>Try again</a>");
                return;
            }

            if (userDAO.registerUser(username, password, email)) {
                response.getWriter().println("Registration successful. <a href='login.html'>Login</a>");
            } else {
                response.getWriter().println("Registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred. Please try again.");
        }
    }
}
