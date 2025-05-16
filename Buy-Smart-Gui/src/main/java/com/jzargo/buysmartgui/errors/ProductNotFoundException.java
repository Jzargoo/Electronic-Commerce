package com.jzargo.buysmartgui.errors;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(){
        super("Catalog is empty");
    }
}
