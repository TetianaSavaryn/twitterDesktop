package com.concordia.controller;

import com.concordia.animation.Shaker;
import com.concordia.database.DBHandler;
import com.concordia.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField firstNameEntry;

    @FXML
    private TextField lastNameEntry;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameEntry;

    @FXML
    private TextField passwordEntry;

    @FXML
    private TextField passwordEntryCopy;

    @FXML
    private Label errorText;

    @FXML
    private Hyperlink homeLink;

    @FXML
    void initialize() {
        this.errorText.setText("");
        signUpButton.setOnAction(actionEvent ->  {

            String firstName = firstNameEntry.getText().trim();
            String lastName = lastNameEntry.getText().trim();
            String username = usernameEntry.getText().trim();
            String password = passwordEntry.getText().trim();
            String passwordCopy = passwordEntryCopy.getText().trim();

            if(!firstName.equals("") &&
                    !lastName.equals("") &&
                    !username.equals("") &&
                    !password.equals("") &&
                    !passwordCopy.equals("")

            ) { if (passwordCopy.equals(password)){

                // Connecting to database
                User user = new User(username, firstName, lastName, password);
                DBHandler dbHandler = new DBHandler();
                dbHandler.sendSignUp(user);

                signUpButton.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("welcome.fxml"));
                changeScene(fxmlLoader, false); }
                else {
                new Shaker(signUpButton).shake();
                this.errorText.setText("Passwords don't match");
            }

            } else {
                new Shaker(signUpButton).shake();
                this.errorText.setText("Please fill out all the fields");
            }
        });

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

//        DBHandler dbHandler = new DBHandler();
//        int numberOfUsers = dbHandler.getUsersNumber();
//        labelUsersNumber.setText("There are " + numberOfUsers + " registered users");
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
