package com.ceraphi.utils.authUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//@Service
//public class EmailServiceImpl implements EmailService {
//    @Value("${spring.mail.username}")
//    private String senderEmail;
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public EmailServiceImpl() {
//    }
//
//    public void sendResetPasswordEmail(String email, String resetLink) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(this.senderEmail);
//        message.setTo(email);
//        message.setSubject("Password Reset");
//        message.setText("Please click on the following link to reset your password: " + resetLink);
//        this.javaMailSender.send(message);
//    }
//}
@Service
public class EmailServiceImpl implements EmailService  {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendResetPasswordEmail(String toEmail, String subject, String messageBody)  {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.fromEmail);
        message.setTo(toEmail);
        message.setSubject("Password Reset");
        message.setText("Please click on the following link to reset your password: " + subject);
        this.javaMailSender.send(message);

    }
}