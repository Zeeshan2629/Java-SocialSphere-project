<%@ page import="java.sql.*, java.util.*" %>
<%@ page session="true" %>
<%@ page import="com.socialsphere.utils.DBConnection" %>

<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.html");
        return;
    }

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
%>

<html>
<head>
    <title>All Posts - SocialSphere</title>
</head>
<body>
    <h2>All Posts</h2>
    <p><a href="createPost.jsp">Create Post</a> | <a href="home.jsp">Home</a> | <a href="logout.jsp">Logout</a></p>
    <%
        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT p.id, p.title, p.content, p.timestamp, u.username " +
                         "FROM posts p JOIN users u ON p.user_id = u.id " +
                         "ORDER BY p.timestamp DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int postId = rs.getInt("id");
    %>
                <div style="border:1px solid black; margin:10px; padding:10px;">
                    <h3><%= rs.getString("title") %></h3>
                    <p><%= rs.getString("content") %></p>
                    <small>Posted by <%= rs.getString("username") %> on <%= rs.getTimestamp("timestamp") %></small>

                    <h4>Comments:</h4>
                    <ul>
                        <%
                            PreparedStatement commentStmt = conn.prepareStatement(
                                "SELECT c.content, u.username, c.timestamp FROM comments c " +
                                "JOIN users u ON c.user_id = u.id WHERE c.post_id = ? ORDER BY c.timestamp"
                            );
                            commentStmt.setInt(1, postId);
                            ResultSet commentRs = commentStmt.executeQuery();
                            while (commentRs.next()) {
                        %>
                            <li>
                                <%= commentRs.getString("content") %> - <%= commentRs.getString("username") %> on <%= commentRs.getTimestamp("timestamp") %>
                            </li>
                        <%
                            }
                            commentRs.close();
                            commentStmt.close();
                        %>
                    </ul>

                    <form action="comment" method="post">
                        <input type="hidden" name="postId" value="<%= postId %>">
                        <textarea name="commentContent" rows="2" cols="40" required></textarea><br>
                        <input type="submit" value="Add Comment">
                    </form>
                </div>
    <%
            }

        } catch (Exception e) {
            out.println("<p>Error loading posts.</p>");
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    %>
</body>
</html>
