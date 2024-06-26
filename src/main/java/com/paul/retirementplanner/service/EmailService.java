package com.paul.retirementplanner.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySources({
        @PropertySource("classpath:email.properties"),
        @PropertySource("classpath:sensitive.properties")
})
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("report@retirementplanner.com");
        message.setTo("paulrothery6@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }
}
