package com.example.tasks.exception;

public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException(Class<?> resourceType, String fieldName, Object fieldValue) {
        super(resourceType.getSimpleName() + " with " + fieldName + " = " + fieldValue + " already exists");
    }
}
