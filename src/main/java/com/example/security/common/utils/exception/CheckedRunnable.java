package com.example.security.common.utils.exception;

@FunctionalInterface
public interface CheckedRunnable {
    void run() throws Exception;
}
