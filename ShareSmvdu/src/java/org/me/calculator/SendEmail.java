/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.calculator;

/**
 *
 * @author DKG
 */
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static String sendEmail(final String emailToAddress,
            final String emailSubject, final String emailBodyText) {
        try {

            final String username = "kumardeepesh05@gmail.com";
            final String password = "05.kumar.deepesh.8838";
            
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            
            Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailToAddress));
            message.setSubject(emailSubject);
            message.setText(emailBodyText);
            Transport.send(message);
            System.out.println("Done");
            return "success";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "fail";
        }
    }
}
