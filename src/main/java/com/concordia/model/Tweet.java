package com.concordia.model;

import com.concordia.database.DBHandler;

import java.sql.Timestamp;

public class Tweet {
    private int id;
    private int userId;
    private String text;
    private Timestamp datePosted;

    public Tweet() {
    }

    public Tweet(int userId, String text, Timestamp datePosted) {
        this.userId = userId;
        this.text = text;
        this.datePosted = datePosted;
    }

    public Tweet(int id, int userId, String text, Timestamp datePosted) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.datePosted = datePosted;
    }

    public int getId() {
        return id;
    }

    public void seId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }

}
