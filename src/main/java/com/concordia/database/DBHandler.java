package com.concordia.database;

import com.concordia.model.Contact;
import com.concordia.model.Tweet;
import com.concordia.model.User;

import java.sql.*;

public class DBHandler extends Config {
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        return dbConnection;
    }

    // Sign up
    public void sendSignUp (User user) {
        String query = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USER_USERNAME + "," +
                Const.USER_FIRSTNAME + "," +
                Const.USER_LASTNAME + "," +
                Const.USER_PASSWORD + ") VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tweet
    public void sendTweet (Tweet tweet) {
        String query = "INSERT INTO " + Const.TWEET_TABLE + "(" +
                Const.TWEET_USER_ID + "," +
                Const.TWEET_TEXT + "," +
                Const.TWEET_DATE_POSTED + ") VALUES(?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, tweet.getUserId());
            preparedStatement.setString(2, tweet.getText());
            preparedStatement.setTimestamp(3, tweet.getDatePosted());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Contact us
    public void sendContact(Contact contact) {

        String query = "INSERT INTO " + Const.CONTACT_TABLE + "(" +
                Const.CONTACT_USERNAME + "," +
                Const.CONTACT_MESSAGE  + ") VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, contact.getUsername());
            preparedStatement.setString(2, contact.getMessage());
            preparedStatement.executeUpdate();
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Login, getting user from db
    public ResultSet getUser (User user) {
        ResultSet resultSet = null;

        if(!user.getUsername().equals("") && !user.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                    Const.USER_USERNAME + "=? AND " +
                    Const.USER_PASSWORD + "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                //System.out.println(query);

                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please enter your credentials");
        }
        return resultSet;
    }

    // Get number of tweets
    public int getTweetsNumber() {

        String query = "SELECT COUNT(*) FROM " + Const.TWEET_TABLE;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get tweet username
    public String getTweetUsername(Tweet tweet) throws SQLException {
        ResultSet resultSet = null;
        String query = "SELECT " + Const.USER_USERNAME + " FROM " + Const.USER_TABLE +
                " WHERE " + Const.USER_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, tweet.getUserId());
            //System.out.println(query);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Get all tweets
    public ResultSet getTweets() {
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + Const.TWEET_TABLE;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Delete tweet
    public void deleteTweet(int userId, int tweetId) {
        String query = "DELETE FROM " + Const.TWEET_TABLE + " WHERE " + Const.TWEET_USER_ID + "=? AND " + Const.TWEET_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tweetId);
            preparedStatement.execute();
            System.out.println(preparedStatement);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update tweet
    public void updateTweet(String text, int userId, int tweetId) {
        String query = "UPDATE " + Const.TWEET_TABLE + " SET " + Const.TWEET_TEXT +
                "=? WHERE " + Const.TWEET_USER_ID + "=? AND " + Const.TWEET_ID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, tweetId);
            preparedStatement.execute();
            //System.out.println(preparedStatement);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
