package com.jm.marketplace.exception;

public class UserUniqueCodeNotFoundException extends RuntimeException{

    public UserUniqueCodeNotFoundException() {
        super();
    }

    public UserUniqueCodeNotFoundException(String message) {
        super(message);
    }

    public UserUniqueCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
