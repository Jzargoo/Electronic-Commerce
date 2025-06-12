package com.jzargo.buysmartgui.ui.template.dialog;

import com.jzargo.shared.model.UserSettingsDto;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import lombok.Setter;

import java.nio.channels.NoConnectionPendingException;
import java.util.Currency;
import java.util.List;
@Setter
public class GeneralSettingsCard {
    @FXML public ChoiceBox<String> currencyPicker;
    @FXML public CheckBox notificationBox;
    @FXML public ChoiceBox<String> languagePicker;
    @FXML public ChoiceBox<String> themePicker;

    private UserSettingsDto cached;
    public void initialize(){

        List<Currency> availableCurrencies = Currency.getAvailableCurrencies()
                .stream().toList();

        availableCurrencies.stream().map(Currency::getCurrencyCode)
                .forEach(curr ->
                    currencyPicker.getItems().add(curr));


        languagePicker.getItems().addAll("English", "Russian");


        themePicker.getItems().addAll("Dark", "light");
    }

    public void initValues() {
        if (cached == null) {
            DialogFactory.showError("Error","Error with server try again later");
           throw new NoConnectionPendingException();
        }


        notificationBox.setSelected(cached.isNotificationsEnabled());
        currencyPicker.getSelectionModel().select(
               cached.getCurrency());
        languagePicker.getSelectionModel().select(
                cached.getLanguage()
        );
        themePicker.getSelectionModel()
                .select(cached.getTheme());
    }

    public UserSettingsDto collectInput() {
       return UserSettingsDto.builder()
               .notificationsEnabled(notificationBox.isSelected())
               .theme(themePicker.getValue())
               .language(languagePicker.getValue())
               .currency(currencyPicker.getValue())
               .build();
    }
}
