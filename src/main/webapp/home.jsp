<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page session="true" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.html");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home - SocialSphere</title>
</head>
<body>
    <h2>Welcome, <%= username %>!</h2>
    <ul>
        <li><a href="createPost.jsp">Create Post</a></li>
        <li><a href="viewPosts.jsp">View Posts</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>
</body>
</html>
