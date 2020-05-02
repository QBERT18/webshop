package de.webshop.eventlisteners;

import de.webshop.constants.UserPermission;
import de.webshop.entities.User;
import de.webshop.entities.VerificationToken;
import de.webshop.events.OnRegistrationSuccessEvent;
import de.webshop.services.MailService;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.MailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
        } catch (MailServiceException e) {
            e.printStackTrace();
        }

    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) throws MailServiceException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        VerificationToken userToken = userDbService.createVerificationToken(user, token);
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/regitrationConfirm.html?token=" + token;

        mailService.sendVerificationMail(user.getEmail(), userToken);
    }
}