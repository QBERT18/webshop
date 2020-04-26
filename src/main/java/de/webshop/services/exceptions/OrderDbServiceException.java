package de.webshop.services.exceptions;

public class OrderDbServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public OrderDbServiceException() {
        super();
    }

    public OrderDbServiceException(String message) {
        super(message);
    }

    public OrderDbServiceException(Throwable cause) {
        super(cause);
    }

    public OrderDbServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
