package com.concordia.controller;

import com.concordia.animation.Shaker;
import com.concordia.database.DBHandler;
import com.concordia.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameEntry;

    @FXML
    private TextField passwordEntry;

    @FXML
    private Button loginButton;

    private DBHandler dbHandler;
    private int userId;

    @FXML
    void initialize() {

        dbHandler = new DBHandler();


        loginButton.setOnAction(actionEvent ->  {
            String username = usernameEntry.getText().trim();
            String password = passwordEntry.getText().trim();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            // Connecting to database
            ResultSet resultSet = dbHandler.getUser(user);
            //System.out.println("Username: " + username + "\nPassword: " + password);

            try {
                if(resultSet != null && resultSet.next()) {
                    userId = resultSet.getInt("id");
                    NewTweetController.userId = userId;
                    showWelcomeScreen(user);
                } else {
                    new Shaker(loginButton).shake();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        signupButton.setOnAction(actionEvent ->  {
            signupButton.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("signUp.fxml"));
            changeScene(fxmlLoader, false);
        });

        usernameEntry.setOnKeyReleased(keyEvent -> handleKeyReleased());
        passwordEntry.setOnKeyReleased(keyEvent -> handleKeyReleased());

    }


    private void handleKeyReleased(){
        String username = usernameEntry.getText().trim();
        String password = passwordEntry.getText().trim();
        boolean disableButton = username.isEmpty() || password.isEmpty();
        loginButton.setDisable(disableButton);
    }
    private void showWelcomeScreen(User user) {
        loginButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("welcome.fxml"));

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

        WelcomeController welcomeController = fxmlLoader.getController();
        welcomeController.setLabels(user.getUsername());
        detailsStage.show();

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

        if (isSignedIn) {
            NewTweetController.userId = userId;
        }
        stage.showAndWait();
    }
}