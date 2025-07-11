package com.socialsphere.dao;

import com.socialsphere.model.Comment;
import java.util.List;
import java.sql.SQLException;

public interface CommentDAO {
    boolean addComment(int postId, int userId, String content) throws SQLException;
    List<Comment> getCommentsByPostId(int postId) throws SQLException;
}
