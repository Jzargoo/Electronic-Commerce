package com.jzargo.buysmartgui.ui.pages;

import com.jzargo.buysmartgui.ElectronicCommerce;
import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;

public class RegisterPageController {

    @FXML private Button back;
    @FXML private TextField password;
    @FXML private TextField username;
    @FXML private TextField emailOrPhone;
    @FXML private TextField confirmPassword;

    @FXML
    static void goBack(ActionEvent ae){
        try {
            FXMLLoader loader = new FXMLLoader(ElectronicCommerce.class
                    .getResource("pages/WelcomePage.fxml"));
            Parent load = loader.load();
            Stage window = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            window.setScene(new Scene(load));
            window.show();
        } catch (IOException e) {
            DialogFactory.showError("Cannot laod welcome page",
                    "Something went wrong, cannot be able to give you access to page");
        }
    }

    @FXML void initialize(){
        back.setOnAction(RegisterPageController::goBack);
    }
    @SneakyThrows @FXML
    void sendRegisterRequest(ActionEvent actionEvent){
        if(emailOrPhone!= null&& username != null &&
            password != null &&
            confirmPassword != null && (confirmPassword.getText().equals(
                    password.getText()))
        ) {
            UserCreateAndUpdateDto dto = null;
            if (emailOrPhone.getText().contains("@")){
                dto = UserCreateAndUpdateDto.builder()
                        .username(username.getText())
                        .Email(emailOrPhone.getText())
                        .password(password.getText())
                        .role("BUYER")
                        .build();
            } else {
                dto = UserCreateAndUpdateDto.builder()
                        .username(username.getText())
                        .phone(emailOrPhone.getText())
                        .password(password.getText())
                        .role("BUYER")
                        .build();
            }

            AuthService authService =AuthService.getInstance();
            int register = authService.register(dto);
            if(register == 201) {
                HomePageController.HomeLoad(actionEvent);
            }
        }
    }


}
