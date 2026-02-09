package com.example.security.notification.service.impl;

import com.example.security.notification.constant.NotificationConstant;
import com.example.security.notification.service.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageProducerImpl implements MessageProducer {

    private final RabbitTemplate rabbitTemplate;


    @Override
    public void sendEmail(String message) {
        rabbitTemplate.convertAndSend(
                NotificationConstant.EXCHANGE,
                NotificationConstant.EMAIL_KEY,
                message
        );
    }

    @Override
    public void sendSMS(String message) {
        rabbitTemplate.convertAndSend(
                NotificationConstant.EXCHANGE,
                NotificationConstant.SMS_KEY,
                message
        );
    }
}
