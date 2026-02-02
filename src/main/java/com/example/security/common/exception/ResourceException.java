package com.example.security.common.exception;

public class ResourceException extends RuntimeException {
    private Object error;

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Object error) {
        super(message);
        this.error = error;
    }
}
