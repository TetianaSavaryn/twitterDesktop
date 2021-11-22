package com.concordia.controller;

import com.concordia.database.DBHandler;
import com.concordia.model.Tweet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AllTweetsController {

    @FXML
    private Button newTweetButton;

    @FXML
    private Hyperlink homeLink;

    @FXML
    private ListView<Tweet> tweetsListView;

    private ObservableList<Tweet> tweetsObservableList = FXCollections.observableArrayList();

    private DBHandler dbHandler;

    @FXML
    void initialize() throws SQLException {
//        Tweet tweet = new Tweet(3,"hello", Timestamp.valueOf(LocalDateTime.now()));
//        Tweet tweet1 = new Tweet(3,"hellowrer", Timestamp.valueOf(LocalDateTime.now()));
//        tweetsObservableList.addAll(tweet, tweet1);
        dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.getTweets();
        while (resultSet.next()){
            Tweet tweet = new Tweet(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("text"),
                    resultSet.getTimestamp("date_posted")
            );
            tweetsObservableList.add(tweet);
        }

        tweetsListView.setItems(tweetsObservableList);
        tweetsListView.setCellFactory(twitterListView -> new TweetCellController());

        homeLink.setOnAction(actionEvent ->  {
            homeLink.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login.fxml"));
            changeScene(fxmlLoader, false);

        });

        newTweetButton.setOnAction(actionEvent ->  {
            newTweetButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("newTweet.fxml"));
            changeScene(fxmlLoader, true);
        });

    }

    private void changeScene(FXMLLoader fxmlLoader, boolean isSignedIn){
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
