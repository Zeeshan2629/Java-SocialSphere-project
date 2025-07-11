package com.socialsphere.model;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int postId;
    private int userId;
    private String content;
    private String username; // Optional
    private Timestamp timestamp;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
