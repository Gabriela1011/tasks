package com.example.tasks.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> resourceType, Object id) {
        super(resourceType.getSimpleName() + " with id " + id + " not found");
    }

    public ResourceNotFoundException(Class<?> resourceType, String fieldName, Object fieldValue) {
        super(resourceType.getSimpleName() + " with " + fieldName + " = " + fieldValue + " not found");
    }
}
