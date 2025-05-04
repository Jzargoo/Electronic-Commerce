package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Objects;

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

    @SneakyThrows
    @FXML
    void sendRegisterRequest(ActionEvent actionEvent){
        if(phone != null&& username != null &&
            password != null && email != null &&
            confirmPassword != null && (confirmPassword.getText().equals(
                    password.getText()))
        ) {

            UserCreateAndUpdateDto dto = UserCreateAndUpdateDto.builder()
                    .username(username.getText())
                    .Email(email.getText())
                    .password(password.getText())
                    .role("BUYER")
                    .phone(phone.getText())
                    .build();

            AuthService authService = new AuthService();
            authService.register(dto);

            HomeLoad(actionEvent);
        }
    }

    private void HomeLoad(ActionEvent ae) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("com/jzargo/buysmartgui/HomePage.fxml"))
        );
        Stage stage = (Stage)((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
