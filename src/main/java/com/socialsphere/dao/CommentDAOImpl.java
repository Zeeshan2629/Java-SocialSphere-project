package com.socialsphere.dao;

import com.socialsphere.model.Comment;
import com.socialsphere.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class CommentDAOImpl implements CommentDAO {

    @Override
    public boolean addComment(int postId, int userId, String content) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)"
            );
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt.setString(3, content);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Comment> getCommentsByPostId(int postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT c.id, c.content, c.timestamp, u.username " +
                            "FROM comments c JOIN users u ON c.user_id = u.id WHERE c.post_id = ? ORDER BY c.timestamp ASC"
            );
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setContent(rs.getString("content"));
                comment.setTimestamp(rs.getTimestamp("timestamp"));
                comment.setUsername(rs.getString("username"));
                comments.add(comment);
            }
        }
        return comments;
    }
}
