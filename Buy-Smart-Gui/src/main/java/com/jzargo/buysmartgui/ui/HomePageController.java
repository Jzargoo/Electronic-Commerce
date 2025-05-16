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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomePageController {

    @FXML private HBox container;

    @FXML private Hyperlink category1;
    @FXML private Hyperlink category2;
    @FXML private Hyperlink category3;


    @FXML
    public void initialize(){

        List<Hyperlink> categoryLinks = List.of(category1, category2, category3);

        ProductService productService = ProductService.getInstance();
        List<ProductReadDto> data = productService.getThreeProducts();
        List<String> categories= CategoryService.getInstance().getCategories(3);

        for (int x=0; x < categories.size(); x++){
            categoryLinks.get(x)
                    .setText(
                            categories.get(x).replace("_"," ")
                    );
        }
        for (ProductReadDto dto: data){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/com/jzargo/buysmartgui/objects/ResultCard.fxml"));
            try {

                byte[] image = productService.getImage(dto.getImage());

                Parent root = fxmlLoader.load();
                ResultCardController controller = fxmlLoader.getController();
                controller.setProduct(dto, image);
                container.getChildren().add(root);

            } catch (IOException e){
                throw new RuntimeException();
            }


        }
    }

    public static void HomeLoad(ActionEvent ae) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(HomePageController.class.getResource("/com/jzargo/buysmartgui/pages/HomePage.fxml"))
        );
        Stage stage = (Stage)((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
