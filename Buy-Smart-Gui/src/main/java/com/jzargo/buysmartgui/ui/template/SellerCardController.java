package com.jzargo.buysmartgui.ui.template;

import com.jzargo.shared.common.Categories;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class SellerCardController {
    public ListView<Categories> categories;
    public TextArea description;
    public TextField nameShop;

    public void initialize(){
        categories.getItems().addAll(Categories.values());
    }

    @Data
    @AllArgsConstructor
    static public class BaseShopInfo{
        List<Categories> categories;
        String description;
        String nameShop;
    }

    public BaseShopInfo collectInfo(){
        return new BaseShopInfo(
                categories.getSelectionModel().getSelectedItems().stream().toList(),
                description.getText(),
                nameShop.getText()
        );
    }
}
