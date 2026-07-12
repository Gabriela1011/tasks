package com.example.tasks.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> resourceType, Object id) {
        super(resourceType.getSimpleName() + " with id " + id + " not found");
    }
}
