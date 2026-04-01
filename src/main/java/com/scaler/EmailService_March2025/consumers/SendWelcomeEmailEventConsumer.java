package com.scaler.EmailService_March2025.consumers;

import com.scaler.EmailService_March2025.dtos.SendEmailDto;
import com.scaler.EmailService_March2025.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Component
public class SendWelcomeEmailEventConsumer {

    private final ObjectMapper objectMapper;

    @Value("${mail.smtp.username}")
    private String smtpUsername;

    @Value("${mail.smtp.password}")
    private String smtpPassword;

    public SendWelcomeEmailEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sendWelcomeEmail", groupId = "emailServiceGroup")
    public void handleSendWelcomeEmailEvent(String message) {

        SendEmailDto emailDto;
        try {
            emailDto = objectMapper.readValue(message, SendEmailDto.class);
        } catch (JsonProcessingException e) {
            System.err.println("[EmailConsumer] Failed to deserialise message: " + e.getMessage());
            return;
        }

        String toEmail = emailDto.getEmail();
        String subject = emailDto.getSubject();
        String body    = emailDto.getBody();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Credentials injected from application.properties / environment variables
        // — not hardcoded in source code
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        };

        Session session = Session.getInstance(props, auth);
        EmailUtil.sendEmail(session, toEmail, subject, body);
    }
}