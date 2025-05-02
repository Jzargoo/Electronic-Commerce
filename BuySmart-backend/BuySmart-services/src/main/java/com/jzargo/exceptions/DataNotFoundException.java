package com.jzargo.exceptions;


import java.io.FileNotFoundException;

public class DataNotFoundException extends FileNotFoundException {
    public DataNotFoundException() {
        super("Nothing found");
    }
    public DataNotFoundException(String s){
        super(s);
    }
}
