package com.socialsphere.dao;

import java.sql.SQLException;
import java.util.List;
import com.socialsphere.model.Post;

public interface PostDAO {
    boolean createPost(String title, String content, int userId) throws SQLException;
    List<Post> getAllPosts() throws SQLException;
}

