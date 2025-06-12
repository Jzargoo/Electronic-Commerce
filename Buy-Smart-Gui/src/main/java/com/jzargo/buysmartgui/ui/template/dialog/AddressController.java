package com.jzargo.buysmartgui.ui.template.dialog;

import com.jzargo.shared.model.AddressDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public class AddressController {

    @FXML private TextField streetField;
    @FXML private TextField cityField;
    @FXML private TextField countryField;
    @FXML private TextField postcodeField;
    @FXML private Button submit;

    @Getter @Setter
    private Stage stage;

    @Getter @Setter
    private AddressDto address;

    @FXML
    public void initialize() {
        postcodeField.setPromptText("5–6 digits");

        submit.setOnAction(event -> {
            String street = streetField.getText().trim();
            String city = cityField.getText().trim();
            String country = countryField.getText().trim();
            String postcode = postcodeField.getText().trim();

            if (street.isEmpty() || city.isEmpty() || country.isEmpty()) {
                DialogFactory.showError("Validation Error", "All fields must be filled.");
                return;
            }

            if (!postcode.matches("\\d{5,6}")) {
                DialogFactory.showError("Validation Error", "Postcode must contain 5–6 digits.");
                return;
            }

            address = AddressDto.builder()
                    .street(street)
                    .city(city)
                    .country(country)
                    .zipCode(postcode)
                    .build();

            stage.close();
        });
    }
}
