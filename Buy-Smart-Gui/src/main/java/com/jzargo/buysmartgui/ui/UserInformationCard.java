package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.services.UserService;
import com.jzargo.shared.model.UserReadDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserInformationCard {

    public Button time;
    public Button phone;
    public Button email;
    public Button username;

    public Label field;
    public Label value;

    @FXML
    public void initialize(){
        UserService instance = UserService.getInstance();
        try {
            UserReadDto user = instance.getUser();

            time.setOnAction(ae -> {
                field.setText("created time:");
                value.setText(String.valueOf(
                        user.getCreatedTime()));
            });
            phone.setOnAction(ae -> {
                field.setText("phone:");
                value.setText(String.valueOf(
                        user.getPhone()));
            });
            email.setOnAction(ae -> {
                field.setText("email:");
                value.setText(String.valueOf(
                        user.getCreatedTime()));
            });
            username.setOnAction(ae -> {
                field.setText("username:");
                value.setText(user.getUsername());
            });

        } catch (URISyntaxException e) {
            DialogController.showError(
                    "Failed to Connect to Server",
                    "The system could not reach the server. Please try again later."
            );
        } catch (IOException e) {
            DialogController.showError(
                    "Connection Problem",
                    "There was a problem receiving data from the server. Please check your internet connection or try again later."
            );
        } catch (InterruptedException e) {
            DialogController.showError(
                    "Request Interrupted",
                    "The request was interrupted unexpectedly. Please try again."
            );
        }
    }
}
