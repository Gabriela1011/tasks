package com.example.tasks.exception;

public class InvalidEmailFormatException extends RuntimeException{
    public InvalidEmailFormatException() {
        super("Invalid email format!");
    }
}
