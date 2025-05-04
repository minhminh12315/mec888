package com.home.mec888.controller.email;

import com.home.mec888.dao.UserDao;
import javafx.event.ActionEvent;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    private UserDao userDao;
    private final String from = "quan.na.2413@aptechlearning.edu.vn";
    private final String passkey = "xcxmpegkarszwyhr";

    public void initialize(){
        userDao = new UserDao();
    }
    public void handleSend (String to, String subject, String content) {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);

            Session s = Session.getInstance(p, new javax.mail.Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(from, passkey);
                }
            });

            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setContent(content, "text/html; charset=utf-8");

            javax.mail.Transport.send(msg);
            System.out.println("Send successfully");
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
