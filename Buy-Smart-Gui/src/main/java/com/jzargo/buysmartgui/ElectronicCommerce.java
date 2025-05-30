package com.jzargo.buysmartgui;

import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.buysmartgui.util.JWTStorage;
import com.jzargo.shared.common.BaseRole;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ElectronicCommerce extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        String s = JWTStorage.loadToken();
        AuthService instance = AuthService.getInstance();
        FXMLLoader fxmlLoader;
        BaseRole i;
        switch ( i = instance.checkPermission(s)){

            case BaseRole.GUEST -> fxmlLoader = new FXMLLoader(ElectronicCommerce.class.getResource("pages/WelcomePage.fxml"));
            case BaseRole.SELLER-> fxmlLoader = new FXMLLoader(ElectronicCommerce.class.getResource("pages/ShopperPage.fxml"));
            case BaseRole.BUYER -> fxmlLoader = new FXMLLoader(ElectronicCommerce.class.getResource("pages/HomePage.fxml"));

            default ->
                    throw new IllegalStateException("Unexpected value: " + i);
        }
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setTitle("Buy-Smart");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}