package com.concordia.controller;

import com.concordia.animation.Shaker;
import com.concordia.database.DBHandler;
import com.concordia.model.Tweet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

public class NewTweetController {
    @FXML
    private Button tweetButton;

    @FXML
    private Label errorText;

    @FXML
    private Hyperlink homeLink;

    @FXML
    private TextArea tweetField;

    @FXML
    private Hyperlink countLabel;

    public static int userId;

    @FXML
    void initialize() {

        DBHandler dbHandler = new DBHandler();
        this.errorText.setText("");

        homeLink.setOnAction(actionEvent ->  {
            homeLink.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login.fxml"));
            //changeScene(fxmlLoader, false);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = fxmlLoader.getRoot();
            Stage detailsStage = new Stage();
            Scene scene = new Scene(root);
            detailsStage.setScene(scene);

            detailsStage.show();
        });

        tweetButton.setOnAction(actionEvent ->  {
            Calendar calendar = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
            String text = tweetField.getText().trim();

            if(!text.equals("")) {
                // Connecting to database
                Tweet tweet = new Tweet(userId, text, timestamp);

                dbHandler.sendTweet(tweet);

                tweetButton.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("allTweets.fxml"));
                changeScene(fxmlLoader, false); }
            else {
                new Shaker(tweetButton).shake();
                this.errorText.setText("Please type your tweet first");
            }
        });

        countLabel.setOnAction(actionEvent ->  {
            countLabel.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("allTweets.fxml"));
            changeScene(fxmlLoader, true);
        });

        int numberOfTweets = dbHandler.getTweetsNumber();
        countLabel.setText("There are " + numberOfTweets + " tweets");

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
        stage.showAndWait();
    }


}
