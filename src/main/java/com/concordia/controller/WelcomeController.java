package com.concordia.controller;

import com.concordia.database.DBHandler;
import com.concordia.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private Button newTweetButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Hyperlink homeLink;

    @FXML
    private Hyperlink countLabel;

    @FXML
    private Button contactButton;

    public static User user;

    @FXML
    void initialize() {

        homeLink.setOnAction(actionEvent ->  {
            homeLink.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login.fxml"));
            changeScene(fxmlLoader, false);
        });

        countLabel.setOnAction(actionEvent ->  {
            countLabel.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            AllTweetsController.user = user;
            fxmlLoader.setLocation(getClass().getResource("allTweets.fxml"));
            changeScene(fxmlLoader, true);
        });

        newTweetButton.setOnAction(actionEvent ->  {
            newTweetButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("newTweet.fxml"));
            changeScene(fxmlLoader, true);
        });

        DBHandler dbHandler = new DBHandler();
        int numberOfTweets = dbHandler.getTweetsNumber();
        countLabel.setText("There are " + numberOfTweets + " tweets");

        contactButton.setOnAction(actionEvent ->  {
            contactButton.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("contactUs.fxml"));
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
        });
    }

    void setLabels(String username) {
        this.welcomeLabel.setText("Welcome, " + user.getUsername());
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
