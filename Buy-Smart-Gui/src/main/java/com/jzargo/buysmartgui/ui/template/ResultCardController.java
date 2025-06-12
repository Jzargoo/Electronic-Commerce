package com.jzargo.buysmartgui.ui.template;

import com.jzargo.shared.model.ProductReadDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class ResultCardController {

    @FXML private ImageView image;
    @FXML private Label name;
    @FXML private Label price;

    public void setProduct(ProductReadDto dto, byte[] productImage){
        image.setImage(
                new Image(
                    new ByteArrayInputStream(productImage)
                )
        );
        name.setText(dto.getName());
        price.setText(
                String.valueOf(dto.getPrice()));
    }


    public void loadProductDetails(ActionEvent actionEvent) {

    }
}
