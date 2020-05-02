package de.webshop.services.impl;

import de.webshop.entities.VerificationToken;
import de.webshop.services.MailService;
import de.webshop.services.exceptions.MailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("MailService")
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSenderImpl mailSender;

    @Autowired
    public MailServiceImpl() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("schultestemail@gmail.com");
        mailSender.setPassword("Asdf123x");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

    }

    public void sendMail(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public static void main(String[] args) {
        MailServiceImpl impl = new MailServiceImpl();
        impl.sendMail("daniel70699@gmail.com", "Testsubject", "Testtext");
    }

    @Override
    public void sendVerificationMail(String mail, VerificationToken token) throws MailServiceException {
        sendMail(mail, "Verification for diekeksticker.com", "Thanks for registration.");
    }

    @Override
    public void sendNewsletterMail(String mail) throws MailServiceException {
        sendMail(mail, "Newsletter for diekeksticker.com", "Here's our newsletter.");
    }

    @Override
    public void sendOrderConfirmationMail(String mail) throws MailServiceException {
        sendMail(mail, "Your order at diekeksticker.com", "Here's your order Confirmation.");
    }
}
