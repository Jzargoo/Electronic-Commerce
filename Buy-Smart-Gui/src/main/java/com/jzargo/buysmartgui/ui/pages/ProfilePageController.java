package com.jzargo.buysmartgui.ui.pages;

import com.jzargo.buysmartgui.ElectronicCommerce;
import com.jzargo.buysmartgui.services.UserService;
import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import com.jzargo.buysmartgui.ui.template.dialog.profileCards.UserInformationCard;
import com.jzargo.shared.model.UserReadDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Setter
public class ProfilePageController {

    @FXML public Button openShop;
    @FXML public Button openInfo;
    @FXML public Button openOrders;
    @FXML public Button addresses;
    @FXML public ImageView icon;
    @FXML public HBox top;
    @FXML public HBox bottom;

    private Image cachedIcon;
    private UserReadDto DTO;

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
            DTO = bytes;
            s.setScene(new Scene(fxml.load()));
            s.show();
        } catch (URISyntaxException e) {
            DialogFactory.showError(
                    "Failed to Load Profile Page",
                    "The profile page could not be opened. Please try again later or contact support."
            );
        } catch (IOException e) {
            DialogFactory.showError(
                    "Connection Problem",
                    "There was an issue communicating with the server. Please check your internet connection or try again later."
            );
        } catch (InterruptedException e) {
            DialogFactory.showError(
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
                DialogFactory.showError(
                        "Image Loading Error",
                        "The profile picture could not be loaded. Please check your connection or try again later."
                );
            } catch (InterruptedException e) {
                DialogFactory.showError(
                        "Operation Interrupted",
                        "Something went wrong while loading the image. Please try again."
                );
            }
        } else {
            icon.setImage(cachedIcon);
        }
        openInfo.setOnAction(this::infoConfigure);
        openShop.setOnAction(this::ShopConfigure);
    }

    private void ShopConfigure(ActionEvent actionEvent) {
        FXMLLoader lawLoader = new FXMLLoader(ElectronicCommerce.class
                .getResource("objects/ProfileInformation/seller/lawInfoCard.fxml"));
        FXMLLoader sellerLoader = new FXMLLoader(ElectronicCommerce.class
                .getResource("objects/ProfileInformation/seller/sellerCard.fxml"));
        try {
            top.getChildren().clear();
            bottom.getChildren().clear();

            top.getChildren().add(lawLoader.load());
            bottom.getChildren().add(sellerLoader.load());

        } catch (IOException e) {
            DialogFactory.showError("Error with configuring", "Try again later" + e);
        }
    }

    private void infoConfigure(ActionEvent ae) {
        FXMLLoader info = new FXMLLoader(ElectronicCommerce.class
                .getResource("objects/ProfileInformation/BasicInformationCard.fxml"));
        FXMLLoader address= new FXMLLoader(ElectronicCommerce.class
                .getResource("objects/ProfileInformation/profileCardAddresses.fxml"));

        try {

            top.getChildren().clear();
            bottom.getChildren().clear();

            top.getChildren().add(info.load());
            bottom.getChildren().add(address.load());

            UserInformationCard controller = info.getController();
            controller.setUserCached(DTO);
        } catch (IOException e) {
            DialogFactory.showError(
                    "Components loading error",
                    "Error with loading component " + e.getMessage()
            );
        }

    }
}
