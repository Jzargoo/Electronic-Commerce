package com.jzargo.buysmartgui.ui.pages;

import com.jzargo.buysmartgui.errors.*;
import com.jzargo.buysmartgui.services.ProductService;
import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import com.jzargo.buysmartgui.util.ProductValidator;
import com.jzargo.shared.common.Categories;
import com.jzargo.shared.common.Tags;
import com.jzargo.shared.model.ProductCreateAndUpdateDto;
import com.jzargo.shared.model.ProductDetails;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Stream;

public class ProductCreatePageController {

    public TextField nameField;
    public TextField priceField;
    public TextArea descriptionArea;

    public ComboBox<Categories> categoryCombo;
    public ListView<Tags> tags;


    public ImageView imagePreview;
    public VBox allImage;

    @FXML public void initialize(){


        imagePreview.setImage(null);
        allImage.getChildren().clear();


        categoryCombo.getItems().addAll(Categories.values());
        categoryCombo.getSelectionModel();

        tags.getItems().addAll(Tags.values());
        tags.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML public void uploadImage(ActionEvent ae) {

        if(imagePreview.getImage() != null && allImage.getChildren().size() <= 7){
                allImage.getChildren().add(imagePreview);
                imagePreview.setImage(null);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg")
        );

        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);


        try(var fis = new FileInputStream(selectedFile)){

            imagePreview.setImage(
                    new Image(
                            new ByteArrayInputStream(fis.readAllBytes()))
            );
        } catch (IOException e) {
            DialogFactory.showError("Cannot find an image",
                    "Something went wrong and could not read the image");
        }
    }


    @FXML public void createButton(ActionEvent actionEvent) {


        if(
                ProductValidator.validateProductInput(
                        priceField.getText(),
                        nameField.getText(),
                        descriptionArea.getText(),
                        categoryCombo.getValue().name(),
                        tags.getSelectionModel().getSelectedItems()
                                .stream().map(Tags::name)
                                .toList(),
                        imagePreview.getImage(),
                        allImage
        )){
          return;
        }

        List<Image> list = new ArrayList<>(
                List.of(imagePreview.getImage()));

        if(!allImage.getChildren().isEmpty()){
            list.addAll(Stream.of(
                (ImageView) allImage.getChildren())
                .map(ImageView::getImage).toList());

        }


        ProductCreateAndUpdateDto build = ProductCreateAndUpdateDto.builder()
                .price(Double.valueOf(
                        priceField.getText()))
                .tags(tags.getSelectionModel().getSelectedItems()
                        .stream()
                        .map(Tags::name)
                        .toList())
                .category(categoryCombo.getValue().name())
                .images(
                        list.stream().map(image -> imageToString(image, image.getUrl().substring(
                                image.getUrl().lastIndexOf('.')
                        ))).toList()
                )
                .description(descriptionArea.getText())
                .name(nameField.getText())
                .build();


        ProductService instance = ProductService.getInstance();
        try {
            ProductDetails product = instance.createProduct(build);
        } catch (IOException | URISyntaxException | InterruptedException e) {
         DialogFactory.showError("Cannot connect with server", "The server is unstable try again later");
        }
    }

    private static String imageToString(Image fxImage, String format)  {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(fxImage, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, format, outputStream);
        } catch (IOException e) {
            DialogFactory.showError("Error with images", "Try again later");
        }
        byte[] byteArray = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(byteArray);
    }
}
