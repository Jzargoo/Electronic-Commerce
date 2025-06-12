package com.jzargo.buysmartgui.ui.template.dialog;


import com.jzargo.buysmartgui.ElectronicCommerce;
import com.jzargo.shared.model.AddressDto;
import com.jzargo.shared.model.UserSettingsDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Optional;

@Getter
@Setter
public class DialogFactory {
    public static AddressDto showAddressDialog(Window owner) {
            try {

                FXMLLoader loader = new FXMLLoader(ElectronicCommerce.class
                        .getResource("template/dialog/AddressDialog.fxml"));
                Parent root = loader.load();


                Stage stage = new Stage();
                stage.setTitle("Add Address");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(owner);
                stage.setScene(new Scene(root));


                AddressController controller = loader.getController();
                controller.setStage(stage);
                stage.showAndWait();

                return controller.getAddress();

            } catch (IOException e) {
                showError("Error", "Cannot load address dialog");
                return null;
            }
        }

    public static UserSettingsDto showSettings(Window parent) {
        try {
            FXMLLoader loader = new FXMLLoader(ElectronicCommerce.class.getResource(
                    "template/dialog/SettingsDialog.fxml"
            ));
            Stage dialog = load(parent, loader);

            SettingsController controller = loader.getController();
            controller.setStage(dialog);
            dialog.showAndWait();

            return controller.getResult();

        } catch (IOException e) {
            DialogFactory.showError("Error", "Could not open settings");
            return null;
        }
    }

    private static Stage load(Window parent, FXMLLoader loader) throws IOException {
        Parent root = loader.load();

        Stage dialog = new Stage();
        dialog.setTitle("Settings");
        dialog.initOwner(parent);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setResizable(true);
        dialog.setScene(new Scene(root));
        return dialog;
    }


    public static void showError(String header, String content) {


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();

    }

    public static boolean showConfirmation(String s) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(s);


        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.isPresent() && buttonType.get() == ButtonType.OK;
    }

    public static void showNameDialog(Window window, String header, String body) {

        try {
        FXMLLoader loader = new FXMLLoader(DialogFactory.class
                .getResource("template/dialog/dialog.fxml"));
        Stage load = load(window, loader);


        NamedController controller = loader.getController();
        controller.getHeader().setText(header);
        controller.getMessage().setText(body);


        load.showAndWait();

        } catch (IOException e) {
            showError("Error", "Try again later");
        }
    }
}
