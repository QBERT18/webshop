package de.webshop.events;

import de.webshop.entities.User;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public class OnRegistrationSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private String appUrl; // whats this??
    private User user; // TODO this should be a DTO instead of a DAO, right? or even better, just the id

    public OnRegistrationSuccessEvent(User user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OnRegistrationSuccessEvent that = (OnRegistrationSuccessEvent) o;
        return Objects.equals(appUrl, that.appUrl) &&
                Objects.equals(user, that.user) &&
                Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUrl, user, source);
    }

    @Override
    public String toString() {
        return "OnRegistrationSuccessEvent{" +
                "appUrl='" + appUrl + '\'' +
                ", user=" + user +
                ", source=" + source +
                '}';
    }
}
