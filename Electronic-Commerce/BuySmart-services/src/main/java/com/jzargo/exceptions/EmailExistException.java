package com.jzargo.exceptions;

public class EmailExistException extends Exception{
    public EmailExistException(String tokenAlreadyExist) {
        super(tokenAlreadyExist);
    }
}
