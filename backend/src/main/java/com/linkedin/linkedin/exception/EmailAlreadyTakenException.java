package com.linkedin.linkedin.exception;

public class EmailAlreadyTakenException extends RuntimeException{

    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
