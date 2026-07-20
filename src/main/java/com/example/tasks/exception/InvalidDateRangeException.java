package com.example.tasks.exception;

public class InvalidDateRangeException extends RuntimeException{
    public InvalidDateRangeException() {
        super("Invalid date range: 'to' date cannot be before 'from' date.");
    }
}
