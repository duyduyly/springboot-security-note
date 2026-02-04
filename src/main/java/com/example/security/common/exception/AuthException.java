package com.example.security.common.exception;

import java.util.Objects;

public class AuthException extends RuntimeException {
    private String code;
    private String message;
    private Exception exception;

    public AuthException(String code, String message) {
        super(message);
        this.message = message;
    }

    public AuthException(String code,String message, Exception e) {
        super(message, e);
        this.code = code;
        this.message = message;
        this.exception = e;
    }

    public String toMessage() {
        String format = String.format("{ \"code\": \"%s\", \"message\": \"%s\" }", this.code, this.message);
        if (Objects.nonNull(exception)){
            format += String.format(", \"exception\": \"%s\" ", exception.getMessage());
        }
        return format;
    }

}
