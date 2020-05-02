package de.webshop.services;

import de.webshop.dataTransferObjects.AddressData;
import de.webshop.entities.Address;
import de.webshop.entities.User;
import de.webshop.entities.VerificationToken;
import de.webshop.services.exceptions.AddressDbServiceException;
import de.webshop.services.exceptions.MailServiceException;

public interface MailService {

    public void sendVerificationMail(String mail, VerificationToken userToken) throws MailServiceException;
    public void sendNewsletterMail(String mail) throws MailServiceException;
    public void sendOrderConfirmationMail(String mail) throws MailServiceException;
}
