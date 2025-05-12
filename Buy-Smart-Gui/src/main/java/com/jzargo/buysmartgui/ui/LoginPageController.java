package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.shared.model.LoginCreateDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

public class LoginPageController {
    @FXML
    private TextField password;
    @FXML
    private TextField username;

    @SneakyThrows
    @FXML
    void SendLoginRequest(ActionEvent ae){
        if (password.getText() != null && username.getText() != null){
            LoginCreateDto loginCreateDto = new LoginCreateDto(username.getText(), password.getText());
            AuthService service = AuthService.getInstance();
            int login = service.login(loginCreateDto);
            if (login == 200) {
                HomePageController.HomeLoad(ae);
            }
            return;
        }
        throw new RuntimeException();
    }

    @FXML
    void handleForgetPassword(){

    }
}
