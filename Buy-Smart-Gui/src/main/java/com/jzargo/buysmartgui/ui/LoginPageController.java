package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.shared.model.LoginCreateDto;
import com.jzargo.shared.model.UserReadDto;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginPageController {
    @FXML
    private TextField password;
    @FXML
    private TextField username;

    @FXML
    void SendLoginRequest(){
        if (password.getText() != null && username.getText() != null){
            LoginCreateDto loginCreateDto = new LoginCreateDto(username.getText(), password.getText());
            AuthService service = new AuthService();
            service.login(loginCreateDto);
            return;
        }
        throw new RuntimeException();
    }

    @FXML
    void handleForgetPassword(){

    }
}
