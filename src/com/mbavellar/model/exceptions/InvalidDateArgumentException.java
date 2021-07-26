package com.mbavellar.model.exceptions;

public class InvalidDateArgumentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidDateArgumentException(String message) {
        super(message);
    }
}
