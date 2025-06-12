package com.jzargo.buysmartgui.util;

import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProductValidator {

        public static boolean validateProductInput(
                String priceText,
                String nameText,
                String descriptionText,
                String selectedCategory,
                List<String> selectedTags,
                Image image, VBox allImage) {

            boolean errors = false;

            if (priceText.isBlank() || !priceText.matches("\\d+(\\.\\d+)?")) {
                DialogFactory.showError("Cannot read price field", "You mismatch price field");
                errors = true;
            }

            if (nameText.isBlank() || nameText.length() <= 5) {
                DialogFactory.showError("Cannot read name field",
                        "You must miss name field or length lower than 5");
                errors = true;
            }

            if (descriptionText.isBlank() || descriptionText.length() < 10) {
                DialogFactory.showError("Cannot read description area",
                        "You must miss description field or length lower than 10");
                errors = true;
            }

            if (selectedCategory == null) {
                DialogFactory.showError("Cannot read category field",
                        "You must select one category");
                errors = true;
            }

            if (selectedTags == null || selectedTags.isEmpty()) {
                DialogFactory.showError("Cannot read tag field",
                        "You must select one or greater tags");
                errors = true;
            }

            if (image == null) {
                DialogFactory.showError("Cannot read image field",
                        "You must select one or greater image");
                errors = true;
            }
            if (allImage.getChildren().size() > 7) {
               DialogFactory.showError("Too many pictures!",
                       "maximum photos is 7");
                errors = true;
            }

            for (Node node : allImage.getChildren()) {
                if (!(node instanceof ImageView)) {
                    DialogFactory.showError("All elements must be imageView", "in box only images");
                    errors = true;
                }
            }
            return errors;
        }
}
