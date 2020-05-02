package de.webshop.eventpublishers;

import de.webshop.entities.User;
import de.webshop.events.OnRegistrationSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class RegistrationEmailEventPublisher {

    public RegistrationEmailEventPublisher(User user, String token) {
        publishEvent(user, token);
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(User user, String token) {
        OnRegistrationSuccessEvent customSpringEvent = new OnRegistrationSuccessEvent(user, token);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
