package com.socialsphere.dao;

import com.socialsphere.utils.DBConnection;
import com.socialsphere.model.Post;

import java.sql.*;
import java.util.*;

public class PostDAOImpl implements PostDAO {

    public boolean createPost(String title, String content, int userId) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO posts (title, content, user_id) VALUES (?, ?, ?)"
            );
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setInt(3, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Post> getAllPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT p.id, p.title, p.content, p.timestamp, u.username " +
                            "FROM posts p JOIN users u ON p.user_id = u.id ORDER BY p.timestamp DESC"
            );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setTimestamp(rs.getTimestamp("timestamp"));
                post.setUsername(rs.getString("username"));
                posts.add(post);
            }
        }
        return posts;
    }
}
