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
    <title>Create Post - SocialSphere</title>
</head>
<body>
    <h2>Create a New Post</h2>
    <form method="post" action="createPost">
        Title:<br>
        <input type="text" name="title" required><br><br>
        Content:<br>
        <textarea name="content" rows="5" cols="40" required></textarea><br><br>
        <input type="submit" value="Post">
    </form>
    <p><a href="home.jsp">Back to Home</a></p>
</body>
</html>
