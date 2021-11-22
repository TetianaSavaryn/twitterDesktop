package com.concordia.controller;

import com.concordia.animation.Shaker;
import com.concordia.database.DBHandler;
import com.concordia.model.Tweet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class TweetCellController extends ListCell<Tweet> {

    @FXML
    private AnchorPane listItemAnchorPane;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label tweetLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private ImageView editIcon;

    @FXML
    private Button updateButton;

    @FXML
    private TextField tweetUpdateEntry;

    private FXMLLoader fxmlLoader;

    private DBHandler dbHandler;
    private String text;

    @FXML
    void initialize() {
        deleteIcon.setOnMouseClicked(mouseEvent -> {
            dbHandler = new DBHandler();
            dbHandler.deleteTweet(NewTweetController.userId, getItem().getId());
            getListView().getItems().remove(getItem());
        });
        editIcon.setOnMouseClicked(mouseEvent -> {
            tweetUpdateEntry.setVisible(true);
            tweetUpdateEntry.setDisable(false);
            tweetUpdateEntry.setText(tweetLabel.getText());
            updateButton.setVisible(true);
            updateButton.setDisable(false);
            tweetLabel.setVisible(false);

            updateButton.setOnAction(actionEvent ->  {
                Calendar calendar = Calendar.getInstance();
                Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
                String text = tweetUpdateEntry.getText().trim();

                if(!text.equals("")) {

                    dbHandler = new DBHandler();
                    dbHandler.updateTweet(text, NewTweetController.userId, getItem().getId());

                    updateButton.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("allTweets.fxml"));
                    changeScene(fxmlLoader, true); }
                else {
                    new Shaker(updateButton).shake();
                }
            });

        });
        deleteIcon.setDisable(handleDisable());
        editIcon.setDisable(handleDisable());
    }

    private boolean handleDisable() {
        if (NewTweetController.userId == getItem().getUserId()) {
            return false;
        } else {
            deleteIcon.setVisible(false);
            editIcon.setVisible(false);
            return true;
        }
    };

    @Override
    protected void updateItem(Tweet tweet, boolean isEmpty) {
        super.updateItem(tweet, isEmpty);

        if(isEmpty || tweet == null) {
            setText(null);
            setGraphic(null);
        } else {
            if(fxmlLoader == null) {
                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("tweetCell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            dbHandler = new DBHandler();
            try {
                usernameLabel.setText(dbHandler.getTweetUsername(tweet));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tweetLabel.setText(tweet.getText());
            dateLabel.setText(tweet.getDatePosted().toString());
            setText(null);
            setGraphic(listItemAnchorPane);
        }
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
