package com.jzargo.buysmartgui.ui.template;

import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Window;



public class FooterController {

    @FXML
    public void showTerms(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        DialogFactory.showNameDialog(window, "Terms and Conditions", "Blank");
    }


    @FXML
    public void showPrivacyPolicy(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        DialogFactory.showNameDialog(window, "Privacy Policy","Blank");
    }

    @FXML
    public void showCustomerService(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        DialogFactory.showNameDialog(window, "Customer Service", "Blank");
    }

}
