package com.example.security.question.exception;

import lombok.Getter;

@Getter
public class QuestionException extends RuntimeException {
    private final String code;

    public QuestionException(String code, String message) {
        super(message);
        this.code = code;
    }

    public QuestionException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
