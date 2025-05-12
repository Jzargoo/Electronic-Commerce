package com.jzargo.buysmartgui.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

public class BannerController {
    public TextField product;

    @FXML
    public void ProductListLoad(ActionEvent ae) throws IOException {
        FXMLLoader.load(
                Objects.requireNonNull(HomePageController.class
                        .getResource("/com/jzargo/buysmartgui/SearchResult.fxml"))
        );
    }

}
