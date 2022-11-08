package com.example.blog.model;

import java.util.Date;

public class HomeModel {
    private int id;
    private String author_name;
    private String title;
    private Date updated_on;
    private String content;
    private Date created_on;
    private String status;
    private int author;

    public HomeModel(int id, String author_name, String title, Date updated_on, String content, Date created_on, String status, int author) {
        this.id = id;
        this.author_name = author_name;
        this.title = title;
        this.updated_on = updated_on;
        this.content = content;
        this.created_on = created_on;
        this.status = status;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }
}
