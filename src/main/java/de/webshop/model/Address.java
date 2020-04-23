package de.webshop.model;

/**
 * Representation of a users address.
 */
public interface Address {

    /**
     * @return the country code of this address
     */
    String getCountryCode();

    /**
     * @return the zip code of this address
     */
    String getZipCode();

    /**
     * @return the city of this address
     */
    String getCity();

    /**
     * @return the street (and house number) of this address
     */
    String getStreet();
}
