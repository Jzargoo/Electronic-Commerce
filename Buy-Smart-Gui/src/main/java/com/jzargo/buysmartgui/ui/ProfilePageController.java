package com.jzargo.buysmartgui.ui;


import com.jzargo.buysmartgui.ElectronicCommerce;
import com.jzargo.buysmartgui.services.UserService;
import com.jzargo.shared.model.UserReadDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Setter
public class ProfilePageController {
    @FXML private ImageView icon;
    private Image cachedIcon;
    private UserReadDto dto;
    public void changeIcon(ActionEvent ae) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        UserService instance = UserService.getInstance();
        try {
            UserReadDto bytes = instance.setUserIcon(selectedFile);
            FXMLLoader fxml = new FXMLLoader(ElectronicCommerce.class.getResource("pages/ProfilePage.fxml"));
            Stage s = (Stage)((Node)ae.getSource()).getScene().getWindow();
            s.setScene(new Scene(fxml.load()));
            s.show();
        } catch (URISyntaxException e) {
            DialogController.showError(
                    "Failed to Load Profile Page",
                    "The profile page could not be opened. Please try again later or contact support."
            );
        } catch (IOException e) {
            DialogController.showError(
                    "Connection Problem",
                    "There was an issue communicating with the server. Please check your internet connection or try again later."
            );
        } catch (InterruptedException e) {
            DialogController.showError(
                    "Operation Interrupted",
                    "Something went wrong during the loading process. Please try again."
            );
        }

}

    public void initialize(){
        UserService instance = UserService.getInstance();
        if (cachedIcon == null) {
            try {
                icon.setImage(
                        new Image(
                                new ByteArrayInputStream(instance.getImageIcon())
                        )
                );
            } catch (IOException e) {
                DialogController.showError(
                        "Image Loading Error",
                        "The profile picture could not be loaded. Please check your connection or try again later."
                );
            } catch (InterruptedException e) {
                DialogController.showError(
                        "Operation Interrupted",
                        "Something went wrong while loading the image. Please try again."
                );
            }
        } else {
            icon.setImage(cachedIcon);
        }
    }
}
