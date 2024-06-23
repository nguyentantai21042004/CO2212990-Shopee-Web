package com.example.demo.services.tes;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Additional {
    public void send(String to, String sub, String msg, final String user, final String pass) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(sub);
        message.setText(msg);

        Transport.send(message);
        System.out.println("Sent message successfully....");
    }
}

