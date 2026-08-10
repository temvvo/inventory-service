package com.challenge.adapter.api.exception;

public class InvalidCsvContentException extends RuntimeException {
    public InvalidCsvContentException(String message) {
        super(message);
    }
}