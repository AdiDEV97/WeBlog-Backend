package com.security.blogs.Payloads;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class GmailSend {

    public boolean sendGmail(String receiver, String sender, String subject, String text) {

        boolean flag = false;

        // Set SMTP Properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        // Set Credentials for mail
        //String username = "adeshsureshvaidya.av@gmail.com";
        String password = "ffvwytqoherijqwg";

        // Create Session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setFrom(new InternetAddress(sender));
            message.setSubject(subject);
            message.setText(text);

            // Sending Mail
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
