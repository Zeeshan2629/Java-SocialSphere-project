package com.socialsphere.servlets;

import com.socialsphere.dao.UserDAO;
import com.socialsphere.dao.UserDAOImpl;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Important: Set content type so HTML renders correctly
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAOImpl();

        try {
            if (userDAO.validateUser(username, password)) {
                int userId = userDAO.getUserId(username);

                // Create session and store userId
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("username", username);

                response.sendRedirect("home.jsp");  // or any other home page you have
            } else {
                out.println("Invalid credentials. <a href='login.html'>Try again</a>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error occurred during login.");
        }
    }
}
