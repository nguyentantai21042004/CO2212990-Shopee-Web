package com.example.demo.services;

import com.example.demo.models.users.User;
import com.example.demo.repositories.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final JavaMailSender emailSender;

    @Override
    public User addUser() {
        return null;
    }

//    public void sendEmail(String to, String subject, String text) {
//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        try {
//            helper.setFrom(fromEmail);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text, true); // true indicates HTML content
//        } catch (MessagingException e) {
//            throw new RuntimeException("Failed to create MimeMessageHelper", e);
//        }
//
//        try {
//            emailSender.send(message);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to send email", e);
//        }
//    }
}
