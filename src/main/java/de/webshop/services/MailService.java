package de.webshop.services;

import de.webshop.services.exceptions.MailServiceException;

public interface MailService {

    /**
     * Sends a verificatio token by e-mail.
     *
     * @param to the receiver
     * @throws MailServiceException if sending failed
     */
    void sendVerificationMail(String to) throws MailServiceException;

    /**
     * Sends a newsletter e-mail.
     *
     * @param to the receiver
     * @throws MailServiceException if sending failed
     */
    void sendNewsletterMail(String to) throws MailServiceException;

    /**
     * Sends a order confirmation e-mail.
     *
     * @param to the receiver
     * @throws MailServiceException if sending failed
     */
    void sendOrderConfirmationMail(String to) throws MailServiceException;
}
