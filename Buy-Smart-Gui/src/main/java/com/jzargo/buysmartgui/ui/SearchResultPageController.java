package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.errors.ProductNotFoundException;
import com.jzargo.buysmartgui.services.ProductService;
import com.jzargo.shared.filters.ProductFilter;
import com.jzargo.shared.model.PageResponse;
import com.jzargo.shared.model.ProductReadDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.Setter;

import java.io.IOException;

@Setter
public class SearchResultPageController {
    private ProductFilter filter;

    @FXML private HBox container;

    @FXML
    void initialize() {
        container.getChildren().clear();
    }

    public void loadProducts(){

        if(filter == null) {
            Label e = new Label("Search filter do not configured, please choose parameters for search");
            container.getChildren().add(e);
            e.getStyleClass().add(".standard-Font-Style");
            return;
        }

        ProductService instance = ProductService.getInstance();
        try {
            PageResponse<ProductReadDto> products = instance.findAll(filter);

            for (ProductReadDto product: products.content()) {

                byte[] image = instance.getImage(product.getImage());

                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/com/jzargo/buysmartgui/objects/ResultCard.fxml"));

                Parent load = loader.load();
                ((ResultCardController) loader.getController()).setProduct(product, image);

                container.getChildren().add(load);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ProductNotFoundException e) {
            Label label = new Label("By that parameters cannot find a product");
            container.getChildren().add(label);
            label.getStyleClass().add(".standard-Font-Style");
        } finally {
            filter = null;
        }
    }
}
