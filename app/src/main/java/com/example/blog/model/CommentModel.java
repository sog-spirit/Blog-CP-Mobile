package com.example.blog.model;

import java.util.Date;

public class CommentModel {
    private int post_id, user_id, comment_id;
    private String username, content;
    private boolean is_deleted;
    private Date created_on;

    public CommentModel(int post_id, int user_id, int comment_id, String username, String content, boolean is_deleted, Date created_on) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.comment_id = comment_id;
        this.username = username;
        this.content = content;
        this.is_deleted = is_deleted;
        this.created_on = created_on;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
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

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }
}
