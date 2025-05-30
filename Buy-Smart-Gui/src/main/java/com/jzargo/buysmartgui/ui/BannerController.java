package com.jzargo.buysmartgui.ui;

import com.jzargo.buysmartgui.ElectronicCommerce;
import com.jzargo.buysmartgui.services.UserService;
import com.jzargo.buysmartgui.util.JWTStorage;
import com.jzargo.shared.filters.ProductFilter;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

public class BannerController {
    @FXML private TextField product;
    @FXML private ImageView bannerImage;
    private final Popup itemsPopup = new Popup();
    private final PauseTransition hideDelay = new PauseTransition(Duration.millis(300));
    private static Image ICON;

    static{
        changeImageIcon();
    }


    @SneakyThrows
     public static void changeImageIcon() {
        UserService instance = UserService.getInstance();
        byte[] imageIcon = instance.getImageIcon();
        ICON = new Image(
                new ByteArrayInputStream(imageIcon)
        );
    }

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

    @FXML
    public void loadCartPage(MouseDragEvent mouseDragEvent) {

    }

    @FXML
    public void loadFavoritePage(MouseDragEvent mouseDragEvent) {

    }

    @FXML
    public void initialize(){
        bannerImage.setImage(ICON);
        bannerImage.setOnMouseEntered(event -> {
            openPopup(event);
            if (!itemsPopup.isShowing()) {
                itemsPopup.show(bannerImage.getScene().getWindow(), event.getScreenX(), event.getScreenY() + 20);
            }
            hideDelay.stop();
        });

        bannerImage.setOnMouseExited(event -> hideDelay.playFromStart());
        hideDelay.setOnFinished(e -> itemsPopup.hide());
    }

    public void openPopup(MouseEvent event) {
        Button profile = new Button("Profile");
        profile.setOnAction(ae -> {
            FXMLLoader loader = new FXMLLoader(ElectronicCommerce.class.getResource("pages/ProfilePage.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            try {
                stage.setScene(new Scene(loader.load()));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        profile.getStyleClass().addAll("standard-Font-Style", "view");

        Button settings = new Button("Settings");
        settings.getStyleClass().addAll("standard-Font-Style", "view");

        Button logout = getButton(event);
        logout.getStyleClass().addAll("standard-Font-Style", "view");

        VBox content = new VBox(profile, settings, logout);
        content.setStyle("-fx-background-color: black; -fx-padding: 10; -fx-spacing: 5; -fx-border-color: black; -fx-border-width: 1;");

        content.setOnMouseEntered(e -> hideDelay.stop());
        content.setOnMouseExited(e -> hideDelay.playFromStart());

        itemsPopup.getContent().clear();
        itemsPopup.getContent().add(content);

        itemsPopup.show(
                ((ImageView) event.getSource()).getScene().getWindow(),
                event.getScreenX(),
                event.getScreenY() + 20
        );
    }

    private static Button getButton(MouseEvent event) {
        Button logout = new Button("Logout");
        logout.setOnAction(ae -> {
            FXMLLoader loader = new FXMLLoader(ElectronicCommerce.class.getResource("pages/WelcomePage.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            try {
                JWTStorage.remove();
                stage.setScene(new Scene(loader.load()));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return logout;
    }
}
