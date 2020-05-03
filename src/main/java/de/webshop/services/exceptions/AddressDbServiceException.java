package de.webshop.services.exceptions;

public class AddressDbServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public AddressDbServiceException() {
        super();
    }

    public AddressDbServiceException(String message) {
        super(message);
    }

    public AddressDbServiceException(Throwable cause) {
        super(cause);
    }

    public AddressDbServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
