package com.jzargo.buysmartgui.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoadPageController {
    @FXML
    private MenuButton from;
    @FXML
    private Button aboutUs;
    @FXML
    private Button signUp;
    @FXML
    private Button catalog;

    @FXML
    public void loginLoad(ActionEvent ae) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/com/jzargo/buysmartgui/pages/LoginPage.fxml")));
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void registerLoad(ActionEvent ae) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/com/jzargo/buysmartgui/pages/RegisterPage.fxml")));
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void AboutUsLoad(ActionEvent ae) throws IOException{

    }
    @FXML
    public void CatalogLoad(ActionEvent ae) throws IOException{

    }
}
