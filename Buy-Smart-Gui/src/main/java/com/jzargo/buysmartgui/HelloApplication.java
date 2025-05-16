package com.jzargo.buysmartgui;

import com.jzargo.buysmartgui.services.AuthService;
import com.jzargo.buysmartgui.util.JWTStorage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        String s = JWTStorage.loadToken();
        AuthService instance = AuthService.getInstance();
        FXMLLoader fxmlLoader;
        if(instance.checkJWT(s)){
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pages/HomePage.fxml"));
        } else{
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pages/WelcomePage.fxml"));
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