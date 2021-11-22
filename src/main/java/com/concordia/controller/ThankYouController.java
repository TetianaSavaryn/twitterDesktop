package com.concordia.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ThankYouController {
    @FXML
    private Label thankLabel;

    @FXML
    private Text messageLabel;

    @FXML
    private Hyperlink homeLink;

    @FXML
    void initialize() {

        homeLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //System.out.println("Home pressed");
                goHome();
            }
        });
    }

    private void goHome(){
        homeLink.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = fxmlLoader.getRoot();
        Stage loginStage = new Stage();
        Scene scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.show();

    }

    void setLabels(String username, String message){
        this.thankLabel.setText("Thank you, \n" + username + "!");
        this.messageLabel.setText("Your message \"" + message + "\" was sent. " +
                "\nWe have received your request, someone will contact you soon");

    }
}
