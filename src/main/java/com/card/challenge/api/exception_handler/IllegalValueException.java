package com.card.challenge.api.exception_handler;

public class IllegalValueException extends RuntimeException {

    public IllegalValueException(String message) {
        super(message);
    }
}