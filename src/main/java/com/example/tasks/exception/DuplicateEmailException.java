package com.example.tasks.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String email) {
        super("This email already exists: " + email);
    }
}
