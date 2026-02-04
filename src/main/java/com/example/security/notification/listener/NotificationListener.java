package com.example.security.notification.listener;

import com.example.security.common.model.payload.MailRequest;
import com.example.security.common.service.MailService;
import com.example.security.notification.constant.NotificationConstant;
import com.example.security.notification.model.message.EmailMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationListener {
    private final MailService mailService;
    private final ObjectMapper objectMapper;
    private final TemplateEngine templateEngine;

    @RabbitListener(queues = NotificationConstant.EMAIL_QUEUE, ackMode = "MANUAL")
    public void noticeEmail(Message message, Channel channel) throws Exception {
        try {
            String strMessage = new String(message.getBody(), StandardCharsets.UTF_8);
            EmailMessage mailMessage = objectMapper.readValue(strMessage, EmailMessage.class);

            //load param
            Map<String, Object> paramMap = mailMessage.getParamMap();
            Context context = new Context();
            paramMap.forEach(context::setVariable); //set param to context thymeleaf
            String htmlContent = templateEngine.process(
                    "mail/" + mailMessage.getTemplateKey(),  //template file name
                    context
            );

            MailRequest otpMail = MailRequest.builder()
                    .to(mailMessage.getTo())
                    .body(htmlContent)
                    .subject(mailMessage.getSubject())
                    .build();
            mailService.sendEmail(otpMail);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            log.info("Email sent to {} Successfully.", mailMessage.getTo());
        } catch (Exception e) {
            log.error("Failed to process message: {}", e.toString());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }


    @RabbitListener(queues = NotificationConstant.SMS_QUEUE, ackMode = "MANUAL")
    public void noticeSMS(Message message, Channel channel) throws Exception {
        try {
            String strMessage = new String(message.getBody(), StandardCharsets.UTF_8);;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(strMessage);
        } catch (Exception e) {
            log.error("SMS Failed to process message: {}", e.toString());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
