package de.webshop.db.dataAccessObjects;

import de.webshop.entities.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> getAddressesByCountryCode(String countryCode);

    List<Address> getAddressesByZipCode(String zipCode);

    List<Address> getAddressesByCity(String city);

    List<Address> getAddressesByStreet(String Street);
}
