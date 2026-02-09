package com.example.security.notification.service;

public interface MessageProducer {
    void sendSMS(String message);
    void sendEmail(String message);
}
