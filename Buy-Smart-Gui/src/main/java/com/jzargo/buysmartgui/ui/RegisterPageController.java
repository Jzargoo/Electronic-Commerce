package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterPageController {
    @FXML
    private TextField phone;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private TextField confirmPassword;

    @FXML
    void sendRegisterRequest(){
        if(phone != null&& username != null &&
            password != null && email != null &&
            confirmPassword != null && (confirmPassword.getText().equals(
                    password.getText()))
        ) {
            UserCreateAndUpdateDto dto = UserCreateAndUpdateDto.builder()
                    .username(username.getText())
                    .Email(email.getText())
                    .password(password.getText())
                    .phone(phone.getText())
                    .build();
            AuthService authService = new AuthService();
            authService.register(dto);
        }
    }
}
