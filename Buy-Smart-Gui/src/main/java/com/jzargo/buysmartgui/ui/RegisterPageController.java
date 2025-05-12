package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

public class RegisterPageController {
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private TextField emailOrPhone;
    @FXML
    private TextField confirmPassword;


    @SneakyThrows
    @FXML
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
