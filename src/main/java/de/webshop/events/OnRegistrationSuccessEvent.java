package de.webshop.events;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public class OnRegistrationSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private long userId;

    public OnRegistrationSuccessEvent(long userId) {
        super(userId);
        this.userId = userId;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        return Objects.equals(userId, that.userId) &&
                Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, source);
    }

    @Override
    public String toString() {
        return "OnRegistrationSuccessEvent{" +
                ", user=" + userId +
                ", source=" + source +
                '}';
    }
}
