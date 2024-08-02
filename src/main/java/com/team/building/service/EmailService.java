package com.team.building.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailService {
    @Autowired

    private JavaMailSender javamailSender;

    public EmailService(JavaMailSender javamailSender) {
        this.javamailSender = javamailSender;
    }

    public void sendSimpleMessage(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javamailSender.send(message);
    }

}
