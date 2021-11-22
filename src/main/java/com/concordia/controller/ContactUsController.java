package com.concordia.controller;

import com.concordia.animation.Shaker;
import com.concordia.database.DBHandler;
import com.concordia.model.Contact;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ContactUsController {
    @FXML
    private Button sendButton;

    @FXML
    private Hyperlink homeLink;

    @FXML
    private TextField usernameEntry;

    @FXML
    private TextArea message;

    @FXML
    void initialize() {

        sendButton.setOnAction(actionEvent -> {
            String username = usernameEntry.getText().trim();
            String messageText = message.getText().trim();

            if(!username.equals("") && !message.equals("")) {

                Contact contact = new Contact();
                contact.setUsername(username);
                contact.setMessage(messageText);
                DBHandler dbHandler = new DBHandler();
                dbHandler.sendContact(contact);

                sendButton.getScene().getWindow().hide();

                //System.out.println("Username: " + username + "\nmessage: " + message);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("thankYou.fxml"));
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = fxmlLoader.getRoot();
                Stage detailsStage = new Stage();
                Scene scene = new Scene(root);
                detailsStage.setScene(scene);

                ThankYouController thankYouController = fxmlLoader.getController();
                thankYouController.setLabels(username, messageText);
                detailsStage.show();


                //changeScene(fxmlLoader, false);

            } else {
                new Shaker(sendButton).shake();
            }

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
        stage.showAndWait();
    }
}
