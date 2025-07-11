package com.socialsphere.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private int userId;
    private String title;
    private String content;
    private Timestamp createdAt;
    private String username;  // join->users

    public Post() {
        // Default constructor
    }

    public Post(int id, int userId, String title, String content, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.createdAt = timestamp;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
