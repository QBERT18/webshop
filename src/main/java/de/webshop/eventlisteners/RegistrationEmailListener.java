package de.webshop.eventlisteners;

import de.webshop.constants.UserPermission;
import de.webshop.entities.User;
import de.webshop.events.OnRegistrationSuccessEvent;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.UserDbServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationEmailListener.class);

    private final UserDbService userDbService;

    @Autowired
    public RegistrationEmailListener(final UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        try {
            confirmRegistration(event);
        } catch (UserDbServiceException e) {
            logger.error("Could not confirm verification " + event, e);
        }

    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) throws UserDbServiceException {
        final User dbUser = userDbService.getUserById(event.getUser().getUserId()).orElseThrow(UserDbServiceException::new);
        final User updatedUser = new User(dbUser);
        updatedUser.setEnabled(true);
        updatedUser.setUserPermission(UserPermission.STANDARD);
        userDbService.updateUser(dbUser, updatedUser);
    }
}