package com.scaler.EmailService_March2025.consumers;

import com.scaler.EmailService_March2025.dtos.SendEmailDto;
import com.scaler.EmailService_March2025.utils.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Component
public class SendWelcomeEmailEventConsumer {
    private ObjectMapper objectMapper;

    public SendWelcomeEmailEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sendWelcomeEmail",groupId = "emailServiceGroup")
    public void handleSendWelcomeEmailEvent(String message){

        SendEmailDto emailDto=objectMapper.readValue(
                message,
                SendEmailDto.class
        );

        String toEmail =emailDto.getEmail();
        String subject=emailDto.getSubject();
        String body=emailDto.getBody();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("work7028@gmail.com", "tytjrlwufsnmppni");
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail,subject, body);
    }

}
