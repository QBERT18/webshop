package de.webshop.services.exceptions;

public class UserDbServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public UserDbServiceException() {
        super();
    }

    public UserDbServiceException(String message) {
        super(message);
    }

    public UserDbServiceException(Throwable cause) {
        super(cause);
    }

    public UserDbServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
