package de.webshop.services;

import de.webshop.dataTransferObjects.AddressData;
import de.webshop.entities.Address;
import de.webshop.services.exceptions.AddressDbServiceException;

public interface AddressDbService {

    public Address saveNewAddress(AddressData newAddress) throws AddressDbServiceException;
}
