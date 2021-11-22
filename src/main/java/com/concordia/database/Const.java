package com.concordia.database;

public class Const {

    // Tables
    public static final String USER_TABLE = "users";
    public static final String TWEET_TABLE = "tweets";
    public static final String CONTACT_TABLE = "contacts";

    // Users table columns
    public static final String USER_ID = "id";
    public static final String USER_USERNAME = "username";
    public static final String USER_FIRSTNAME = "first_name";
    public static final String USER_LASTNAME = "last_name";
    public static final String USER_PASSWORD = "password";

    // Tweet table columns
    public static final String TWEET_ID = "id";
    public static final String TWEET_USER_ID = "user_id";
    public static final String TWEET_TEXT = "text";
    public static final String TWEET_DATE_POSTED = "date_posted";

    // Contact table columns
    public static final String CONTACT_ID = "id";
    public static final String CONTACT_USERNAME = "username";
    public static final String CONTACT_MESSAGE = "message";
}
