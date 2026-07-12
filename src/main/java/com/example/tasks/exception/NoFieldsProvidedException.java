package com.example.tasks.exception;

public class NoFieldsProvidedException extends RuntimeException{
    public NoFieldsProvidedException(String message) {
        super(message);
    }
}
