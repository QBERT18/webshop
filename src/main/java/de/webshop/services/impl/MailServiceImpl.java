package de.webshop.services.impl;

import de.webshop.entities.VerificationToken;
import de.webshop.services.MailService;
import de.webshop.services.exceptions.MailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.Properties;

@Service("MailService")
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        mailSender = javaMailSender;
    }

    private void sendMail(final String to, final String subject, final String text) throws MailException {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
        }catch (MailException e) {
            // did not send mail :-(
        }
    }

    @Override
    public void sendVerificationMail(String mail, VerificationToken token) throws MailServiceException {
        if (mail.isEmpty()) {
            throw new MailServiceException("The mailaddress can't be empty!");
        }
        sendMail(mail, "Verification for diekekse.com", "Thanks for your registration! Press the " +
                "following link to verificate your account! " + "http://localhost:8080/verificationSuccess?token=" + token.getToken());
    }
}
