package com.jzargo.buysmartgui.ui.template.dialog.profileCards;

import com.jzargo.buysmartgui.services.AddressService;
import com.jzargo.buysmartgui.ui.template.dialog.DialogFactory;
import com.jzargo.shared.model.AddressDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ProfileCardAddressesController {

    @FXML public TableColumn<AddressDto, String> postcodeColumn;
    @FXML public TableColumn<AddressDto, String> streetColumn;
    @FXML public TableColumn<AddressDto, String> cityColumn;
    @FXML public TableColumn<AddressDto, String>countryColumn;
    @FXML public TableView<AddressDto> addressTable;
    private ObservableList<AddressDto> ADDRESSES;

    public void initialize() {
       AddressService addressService= AddressService.getInstance();
        List<AddressDto> addresses = null;
        try {
            addresses = addressService.getAddresses();
        } catch (URISyntaxException | IOException  e) {
            DialogFactory.showError(
                    "Bad request",
                    "Cannot connect to server please try again later"
            );
        } catch (InterruptedException e) {
            DialogFactory.showError(
                    "Problem with connection",
                    "Cannot connect to server  because you do not have access to internet"
            );
        }

        ADDRESSES = FXCollections.observableArrayList(addresses != null ?
                        addresses :
                        new ArrayList<>()
                );

        addressTable.setItems(ADDRESSES);
    }
    @FXML
    public void handleAddAddress(ActionEvent ae){
        AddressDto addressDto = null;
        try {

        addressDto = DialogFactory.showAddressDialog(
                ((Node) ae.getSource())
                        .getScene().getWindow()
        );
        } catch (IllegalStateException e){
            DialogFactory.showError("Invalid address",
                    "some fields in the dialog form did not fill");
        }

        AddressService instance = AddressService.getInstance();
        try {
            AddressDto response = instance.addAddress(addressDto);
            ADDRESSES.add(response);
        } catch (IOException | InterruptedException e) {
            DialogFactory.showError("Error with connection",
                    "cannot connect with server please try again later");
        }
        addressTable.setItems(ADDRESSES);
    }
}
