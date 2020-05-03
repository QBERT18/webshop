package de.webshop.eventlisteners;

import de.webshop.entities.User;
import de.webshop.entities.VerificationToken;
import de.webshop.events.OnRegistrationSuccessEvent;
import de.webshop.services.MailService;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.MailServiceException;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

    private final UserDbService userDbService;
    private final MailService mailService;

    @Autowired
    public RegistrationEmailListener(UserDbService userDbService, MailService mailService) {
        this.userDbService = userDbService;
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MailServiceException | UserDbServiceException e) {
            e.printStackTrace();
        }

    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) throws MailServiceException, UserDbServiceException {
        long userId = event.getUserId();
        String token = UUID.randomUUID().toString();
        VerificationToken userToken = userDbService.createVerificationToken(userId, token);
        Optional<User> user = userDbService.getUserById(userId);
        String verificationEmail = "";
        if (user.isPresent()) {
            verificationEmail = user.get().getEmail();
        }
        
        mailService.sendVerificationMail(verificationEmail, userToken);
    }
}