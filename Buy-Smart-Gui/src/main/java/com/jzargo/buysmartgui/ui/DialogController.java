package com.jzargo.buysmartgui.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
@Getter
@Setter
public class DialogController {
    @FXML
    private TextArea message;

    private Stage dialogStage;
    @FXML
    private Label header;

    @SneakyThrows
    public static void showNameDialog(Window parentWindow, String header, String message) {
            FXMLLoader loader = new FXMLLoader(DialogController.class.
                    getResource("/com/jzargo/buysmartgui/template/dialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(header);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(parentWindow);
            dialogStage.setScene(new Scene(root));

            DialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.getMessage().setText(message);
            controller.getHeader().setText(header);

            dialogStage.showAndWait();
    }

    public static void showSettings(ActionEvent actionEvent) {

    }

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
