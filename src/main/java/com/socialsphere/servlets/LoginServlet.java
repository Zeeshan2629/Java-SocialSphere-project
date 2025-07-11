package com.socialsphere.servlets;

import com.socialsphere.dao.UserDAO;
import com.socialsphere.dao.UserDAOImpl;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAOImpl();

        try {
            if (userDAO.validateUser(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("userId", userDAO.getUserId(username));
                response.sendRedirect("home.jsp");
            } else {
                response.getWriter().println("Invalid credentials. <a href='login.html'>Try again</a>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred during login.");
        }
    }
}
