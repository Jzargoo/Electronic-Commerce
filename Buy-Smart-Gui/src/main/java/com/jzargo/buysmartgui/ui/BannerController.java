package com.jzargo.buysmartgui.ui;

import com.jzargo.shared.filters.ProductFilter;
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

public class BannerController {
    @FXML private TextField product;

    @FXML
    public void ProductListLoad(ActionEvent ae) throws IOException {
        FXMLLoader load = new FXMLLoader(
                getClass().getResource("/com/jzargo/buysmartgui/pages/SearchResultPage.fxml"));
        Parent root = load.load();

        SearchResultPageController controller = load.getController();
        controller.setFilter(
                ProductFilter.builder()
                        .name(product.getText())
                        .build()
        );

        controller.loadProducts();

        Stage s =(Stage) ((Node)ae.getSource()).getScene().getWindow();
        s.setScene(new Scene(root));
        s.show();
    }
    @SneakyThrows
    @FXML
    public void loadHome(ActionEvent ae){
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/com/jzargo/buysmartgui/pages/HomePage.fxml")
        ));
        Stage s = (Stage) ((Node)ae.getSource()).getScene().getWindow();
        s.setScene(new Scene(root));
        s.show();
    }

}
