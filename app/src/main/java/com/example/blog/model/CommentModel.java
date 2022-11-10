package com.example.blog.model;

import java.util.Date;

public class CommentModel {
    private int post, user, id;
    private String username, content;
    private boolean is_delete;
    private Date created_on;

    public CommentModel(int post, int user, int id, String username, String content, boolean is_delete, Date created_on) {
        this.post = post;
        this.user = user;
        this.id = id;
        this.username = username;
        this.content = content;
        this.is_delete = is_delete;
        this.created_on = created_on;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }
}
