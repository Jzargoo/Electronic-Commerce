package com.jzargo.exceptions;

import java.io.FileNotFoundException;

public class ProductNotFoundException extends FileNotFoundException {
    public ProductNotFoundException(int id) {
        super("Product with id " + id + " not found");
    }
}
