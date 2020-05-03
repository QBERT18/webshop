package de.webshop.services.impl;

import de.webshop.dataTransferObjects.AddressData;
import de.webshop.db.dataAccessObjects.AddressRepository;
import de.webshop.entities.Address;
import de.webshop.services.AddressDbService;
import de.webshop.services.exceptions.AddressDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addressDbService")
public class AddressDbServiceImpl implements AddressDbService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressDbServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address saveNewAddress(AddressData newAddress) throws AddressDbServiceException {
        try {
            final Address address = Address.from(newAddress);
            return addressRepository.save(address);
        } catch (IllegalArgumentException e) {
            throw new AddressDbServiceException("New address was null or contains invalid data", e);
        }
    }
}
