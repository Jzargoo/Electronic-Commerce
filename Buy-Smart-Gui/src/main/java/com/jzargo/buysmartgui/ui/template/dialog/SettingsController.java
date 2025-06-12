package com.jzargo.buysmartgui.ui.template.dialog;

import com.jzargo.buysmartgui.ElectronicCommerce;
import com.jzargo.buysmartgui.services.UserService;
import com.jzargo.shared.model.UserSettingsDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public class SettingsController {

    @FXML private VBox content;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @Setter
    private Stage stage;
    @Getter
    private UserSettingsDto result;

    @FXML
    public void initialize() {
        try {
            UserSettingsDto dto = UserService.getInstance().getUserSettings();

            FXMLLoader loader = new FXMLLoader(ElectronicCommerce.class
                    .getResource("template/dialog/generalSettingsCard.fxml"));
            Parent settingsCard = loader.load();

            GeneralSettingsCard controller = loader.getController();
            controller.setCached(dto);
            controller.initValues();

            content.getChildren().add(settingsCard);

            saveButton.setOnAction(e -> {
                UserSettingsDto updated = controller.collectInput();
                if (updated == null) {
                    DialogFactory.showError("Invalid data", "Please fix input");
                    return;
                }
                try {
                    result = UserService.getInstance().setSettings(updated);
                    stage.close();
                } catch (Exception ex) {
                    DialogFactory.showError("Error", "Could not save settings");
                }
            });

            cancelButton.setOnAction(e -> {
                boolean confirm = DialogFactory.showConfirmation("Are you sure that you want to discard changes?");
                if (confirm) stage.close();
            });

        } catch (Exception e) {
            DialogFactory.showError("Error", "Could not load settings");
        }
    }
}