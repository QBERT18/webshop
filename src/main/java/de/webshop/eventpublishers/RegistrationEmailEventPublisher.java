package de.webshop.eventpublishers;

import de.webshop.events.OnRegistrationSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEmailEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(long userId) {
        OnRegistrationSuccessEvent customSpringEvent = new OnRegistrationSuccessEvent(userId);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
