package com.jzargo.buysmartgui.ui.pages;

import com.jzargo.buysmartgui.ElectronicCommerce;
import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import com.jzargo.shared.model.LoginCreateDto;
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

public class LoginPageController {
    @FXML private TextField password;
    @FXML private TextField username;
    @FXML private Button back;

    @SneakyThrows @FXML
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
    @FXML void initialize(){
        back.setOnAction(RegisterPageController::goBack);
    }


    @FXML void handleForgetPassword(){

    }
}
