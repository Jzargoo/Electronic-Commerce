package com.jzargo.buysmartgui.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Window;

import static com.jzargo.buysmartgui.ui.DialogController.showNameDialog;


public class FooterController {

    @FXML
    public void showTerms(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        showNameDialog(window, "Terms and Conditions", "Blank");
    }


    @FXML
    public void showPrivacyPolicy(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        showNameDialog(window, "Privacy Policy","Blank");
    }

    @FXML
    public void showCustomerService(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        showNameDialog(window, "Customer Service", "Blank");
    }

}
