package com.jzargo.buysmartgui.util;

import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class UILawInfoValidator {

    private static final Map<String, String> TOO_PATTERNS = Map.of(
            "Physical_address", ".+",
            "Law_address", ".+",
            "Basis_of_authority", ".+",
            "Business_identification_number", "\\d{12}",
            "Business_name", ".{3,}",
            "CEO_full_name", "^[A-Z][a-z]+( [A-Z][a-z]+){1,2}$" // Имя с заглавными
    );

    public static boolean validateTOO(HashMap<String, TextField> inputTOO) {
        for (Map.Entry<String, String> entry : TOO_PATTERNS.entrySet()) {
            String key = entry.getKey();
            String pattern = entry.getValue();

            TextField field = inputTOO.get(key);

            if (field == null) {
                DialogFactory.showError("Missing field", "Field \"" + formatKey(key) + "\" is not provided.");
                return false;
            }

            String text = field.getText();
            if (text == null || text.trim().isEmpty()) {
                DialogFactory.showError("Empty field", "Field \"" + formatKey(key) + "\" cannot be empty.");
                return false;
            }

            if (!text.matches(pattern)) {
                DialogFactory.showError("Invalid field", "Field \"" + formatKey(key) + "\" is incorrectly formatted.");
                return false;
            }
        }

        return true;
    }

    private static String formatKey(String key) {
        return key.replace('_', ' ').substring(0, 1).toUpperCase() + key.replace('_', ' ').substring(1);
    }
}
