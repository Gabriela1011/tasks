package com.example.tasks.exception;

public class NoSearchCriteriaProvidedException extends RuntimeException{
    public NoSearchCriteriaProvidedException() {
        super("At least one search criterion must be provided");
    }
}
