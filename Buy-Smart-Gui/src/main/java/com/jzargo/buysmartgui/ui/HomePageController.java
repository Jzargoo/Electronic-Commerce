package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.services.CategoryService;
import com.jzargo.buysmartgui.services.ProductService;
import com.jzargo.shared.common.Categories;
import com.jzargo.shared.filters.ProductFilter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePageController {

    @FXML private HBox container;

    @FXML private Hyperlink category1;
    @FXML private Hyperlink category2;
    @FXML private Hyperlink category3;

    private static List<ProductReadDto> DATA;
    private static List<String> CATEGORIES;
    private static List<byte[]> IMAGES = new ArrayList<>();

    static {
        init();
    }
    public void initialize(){
        List<Hyperlink> categoryLinks = List.of(category1, category2, category3);

        for (int x=0; x < CATEGORIES.size(); x++){
            categoryLinks.get(x)
                    .setText(
                            CATEGORIES.get(x).replace("_"," ")
                    );
        }
        for (int i = 0; i < DATA.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/com/jzargo/buysmartgui/objects/ResultCard.fxml"));
            try {


                Parent root = fxmlLoader.load();
                ResultCardController controller = fxmlLoader.getController();
                controller.setProduct(DATA.get(i), IMAGES.get(i));
                container.getChildren().add(root);

            } catch (IOException e){
                throw new RuntimeException();
            }


        }
    }
    public static void init(){


        ProductService productService = ProductService.getInstance();
        DATA = productService.getThreeProducts();
        List<Boolean> list = DATA.stream().map(dto ->
                IMAGES.add(
                        productService.getImage(dto.getImage())
                )
        ).toList();
        CATEGORIES= CategoryService.getInstance().getCategories(3);

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

    public void loadSearchResultPage(ActionEvent ae) throws IOException {
        Hyperlink source = (Hyperlink) ae.getSource();
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/jzargo/buysmartgui/pages/SearchResultPage.fxml")
        );
        Parent load = loader.load();
        ProductFilter build = ProductFilter.builder()
                .category(
                        Categories.valueOf(source.getText().replace(' ', '_'))
                )
                .build();
        SearchResultPageController controller = loader.getController();
        controller.setFilter(build);
        controller.loadProducts();
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        stage.setScene(new Scene(load));
        stage.show();
    }
}
