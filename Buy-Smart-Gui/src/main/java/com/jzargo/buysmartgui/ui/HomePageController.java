package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.services.CategoryService;
import com.jzargo.buysmartgui.services.ProductService;
import com.jzargo.shared.model.ProductReadDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomePageController {

    @FXML private VBox productCard1;
    @FXML private VBox productCard2;
    @FXML private VBox productCard3;

    @FXML private ImageView productCard2Image;
    @FXML private ImageView productCard3Image;
    @FXML private ImageView productCard1Image;

    @FXML private Label productCard3Price;
    @FXML private Label productCard1Price;
    @FXML private Label productCard2Price;

    @FXML private Hyperlink category1;
    @FXML private Hyperlink category2;
    @FXML private Hyperlink category3;


    @FXML
    public void initialize(){

        List<VBox> cards = List.of(productCard3,productCard2,productCard1);
        List<ImageView> images = List.of(productCard1Image, productCard2Image, productCard3Image);
        List<Label> labels = List.of(productCard1Price, productCard2Price, productCard3Price);
        List<Hyperlink> categoryLinks = List.of(category1, category2, category3);

        ProductService productService = ProductService.getInstance();
        List<ProductReadDto> data = productService.getThreeProducts();
        List<String> categories= CategoryService.getInstance().getCategories(3);

        int dataSize = data.size();

        for (int i = 0; i < 3 - dataSize; i++) {
            cards.get(i).setVisible(false);
            cards.get(i).setManaged(false);
        }

        for (int i=0; i < dataSize; i++){
            byte[] image = productService.getImage(
                    data.get(i).getImage());

            images.get(i).setImage(
                    new Image(
                        new ByteArrayInputStream(image)
                    )
            );
            labels.get(i).setText(
                    data.get(i).getPrice() + " $"
            );

        }
        for (int x=0; x < categories.size(); x++){
            categoryLinks.get(x)
                    .setText(
                            categories.get(x).replace("_"," ")
                    );
        }


    }
    @FXML
    public void ProductListLoad(ActionEvent ae){
    }

    public static void HomeLoad(ActionEvent ae) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(HomePageController.class.getResource("/com/jzargo/buysmartgui/HomePage.fxml"))
        );
        Stage stage = (Stage)((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
