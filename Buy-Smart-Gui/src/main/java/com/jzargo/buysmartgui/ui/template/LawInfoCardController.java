package com.jzargo.buysmartgui.ui.template;

import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import com.jzargo.buysmartgui.util.UILawInfoValidator;
import com.jzargo.shared.common.SubjectType;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class LawInfoCardController {

    public VBox content;
    public TextField iban;
    public TextField bankBik;
    public TextField bankName;
    public ComboBox<SubjectType> subjectType;

    private final HashMap<String, TextField> inputIP = new HashMap<>();
    private final HashMap<String, TextField> inputTOO = new HashMap<>();

    private final List<Supplier<VBox>> pages = new ArrayList<>();
    private int currentPage = 0;

    public void initialize() {
        subjectType.getItems().addAll(SubjectType.TOO, SubjectType.IP);

        pages.add(this::createBaseInfoPage);
    }

    public void nextPage() {
        if (currentPage + 1 < pages.size()) {
            content.getChildren().setAll(
                    pages.get(++currentPage).get().getChildren());
        }
    }

    public void previousPage() {
        if (currentPage - 1 >= 0) {
            content.getChildren().setAll(pages.get(--currentPage).get().getChildren());
        }
    }

    public void tooOrIp() {
        SubjectType selected = subjectType.getSelectionModel().getSelectedItem();
        if (selected == null) {
            DialogFactory.showError("Subject type is not selected",
                    "You might miss to select subject type in the comboBox");
            return;
        }

        while (pages.size() > 1) {
            pages.removeAll(
                    pages.subList(1, pages.size())
            );
        }

        inputIP.clear();
        inputTOO.clear();

        if (selected == SubjectType.IP) {
            pages.add(this::createIpInfoPage);
            pages.add(this::createIpDocsPage);
        } else {
            pages.add(this::createTooInfoPage);
            pages.add(this::createTooDocsPage);
        }

        currentPage = 0;
        content.getChildren().setAll(pages.get(currentPage).get().getChildren());
    }

    private VBox createIpDocsPage() {
        return new VBox(
                fileChooserField("IP registration certificate", inputIP),
                fileChooserField("Power of attorney (if not individual entrepreneur)", inputIP),
                fileChooserField("License", inputIP)
        );
    }


    private VBox createBaseInfoPage() {
        VBox page = new VBox(10);
        page.getChildren().addAll(iban, bankBik, bankName);

        inputTOO.put("iban", iban);
        inputTOO.put("bankName", bankName);
        inputTOO.put("bankBik", bankBik);

        return page;
    }

    private VBox createIpInfoPage() {


        TextField name = designedField("full name");
        TextField address = designedField("Address registration");
        TextField tin = designedField("Taxpayer Identification Number");


        VBox vBox = new VBox(name,address,tin);


        inputIP.put(toKey(name.getText()), name);
        inputIP.put(toKey(tin.getText()), name);
        inputIP.put(toKey(address.getText()), name);


        return vBox;
    }
    private VBox createTooInfoPage() {


        TextField bin = designedField("Business identification number");
        TextField name = designedField("Business name");
        TextField ceo = designedField("CEO full name");

        inputTOO.put(toKey(bin.getText()), bin);
        inputTOO.put(toKey(name.getText()), name);
        inputTOO.put(toKey(ceo.getText()), ceo);

        return new VBox(bin, name, ceo);
    }

    private String toKey(String s) {
        return s.replace(' ', '_');
    }

    private VBox createTooDocsPage() {


        TextField physical = designedField("Physical address");
        TextField law = designedField("Law address");
        TextField basis = designedField("Basis of authority");


        inputTOO.put(toKey(physical.getText()), physical);
        inputTOO.put(toKey(law.getText()), law);
        inputTOO.put(toKey(basis.getText()), basis);

        return new VBox(physical, law, basis);
    }


    private TextField designedField(String prompt) {

        TextField field = new TextField();
        field.setText(prompt);
        field.getStyleClass().addAll("rounding", "standard-Font-Style", "first-bg");


        return field;
    }

    //Here field as parameter because work as one time with IP and TOO
    private HBox fileChooserField(String label, HashMap<String, TextField> ipOrTOO) {


        TextField textField = designedField(label);
        textField.setEditable(false);
        ipOrTOO.put(toKey(label), textField);


        Button button = new Button("Search");

        button.setOnAction(ae -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Documents", "*.pdf", "*.png", "*.jpg")
            );
            File file = fileChooser.showOpenDialog(((Node) ae.getSource()).getScene().getWindow());
            if (file != null) {
                textField.setText(file.getAbsolutePath());
            }
        });


        HBox hbox = new HBox(5, textField, button);
        hbox.setPadding(new Insets(0, 5, 0, 0));


        return hbox;
    }

    public void becomeSeller(ActionEvent actionEvent) {
        if (subjectType.getSelectionModel().getSelectedItem() == null) {
            DialogFactory.showError("Subject type is not selected",
                    "You might miss to select subject type in the comboBox");
            return;
        }

        if (subjectType.getSelectionModel()
                .getSelectedItem().equals(SubjectType.IP) && UILawInfoValidator.validateTOO(inputIP)) {
            return;

        } else if (subjectType.getSelectionModel()
                .getSelectedItem().equals(SubjectType.TOO) && !UILawInfoValidator.validateTOO(inputTOO)) {
            return;
        }

        // If we reach here, validation passed
        if (subjectType.getSelectionModel()
                .getSelectedItem().equals(SubjectType.TOO)) {
            UILawInfoValidator.validateTOO(inputTOO);
        } else{
            UILawInfoValidator.validateTOO(inputIP);
        }
    }
}
