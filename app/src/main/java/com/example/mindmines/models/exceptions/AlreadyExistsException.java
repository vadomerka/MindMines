package com.example.mindmines.models.exceptions;

public class AlreadyExistsException extends Exception {
    protected String message;

    public AlreadyExistsException(String exMessage) {
        message = exMessage;
    }

    public String getMessage() {
        return message;
    }
}
