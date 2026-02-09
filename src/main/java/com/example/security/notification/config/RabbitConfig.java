package com.example.security.notification.config;

import com.example.security.notification.constant.NotificationConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // ---------- EXCHANGE ----------
    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(NotificationConstant.EXCHANGE);
    }

    // ---------- EMAIL ----------
    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(NotificationConstant.EMAIL_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key",
                        NotificationConstant.EMAIL_DLQ)
                .build();
    }

    @Bean
    public Queue emailDlq() {
        return QueueBuilder.durable(NotificationConstant.EMAIL_DLQ).build();
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(notificationExchange())
                .with(NotificationConstant.EMAIL_KEY);
    }

    // ---------- SMS ----------
    @Bean
    public Queue smsQueue() {
        return QueueBuilder.durable(NotificationConstant.SMS_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key",
                        NotificationConstant.SMS_DLQ)
                .build();
    }

    @Bean
    public Queue smsDlq() {
        return QueueBuilder.durable(NotificationConstant.SMS_DLQ).build();
    }

    @Bean
    public Binding smsBinding() {
        return BindingBuilder
                .bind(smsQueue())
                .to(notificationExchange())
                .with(NotificationConstant.SMS_KEY);
    }
}
