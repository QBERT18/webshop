package de.webshop.services.impl;

import de.webshop.services.MailService;
import de.webshop.services.exceptions.MailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("userDbService")
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

    private void sendMail(final String to, final String subject, final String text) throws MailException {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendVerificationMail(final String to) throws MailServiceException {
        try {
            sendMail(to, "Verification for diekeksticker.com", "Thanks for registration.");
        } catch (final MailException e) {
            throw new MailServiceException("Sending registration verification mail failed", e);
        }
    }

    @Override
    public void sendNewsletterMail(final String to) throws MailServiceException {
        try {
            sendMail(to, "Newsletter for diekeksticker.com", "Here's our newsletter.");
        } catch (final MailException e) {
            throw new MailServiceException("Sending newsletter mail failed", e);
        }
    }

    @Override
    public void sendOrderConfirmationMail(final String to) throws MailServiceException {
        try {
            sendMail(to, "Your order at diekeksticker.com", "Here's your order Confirmation.");
        } catch (final MailException e) {
            throw new MailServiceException("Sending order confirmation mail failed", e);
        }
    }
}
