package de.webshop.services.exceptions;

public class ProductDbServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProductDbServiceException() {
        super();
    }

    public ProductDbServiceException(String message) {
        super(message);
    }

    public ProductDbServiceException(Throwable cause) {
        super(cause);
    }

    public ProductDbServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
