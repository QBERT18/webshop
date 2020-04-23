package de.webshop.model;

import java.util.Objects;

/**
 * Implementation of {@link Address}.
 */
public class AddressImpl implements Address {

    private final String countryCode;
    private final String zipCode;
    private final String city;
    private final String street;

    /**
     * @param countryCode must be non-null
     * @param zipCode     must be non-null
     * @param city        must be non-null
     * @param street      must be non-null
     * @throws NullPointerException if any argument is null
     */
    public AddressImpl(final String countryCode, final String zipCode, final String city, final String street) {
        this.countryCode = Objects.requireNonNull(countryCode);
        this.zipCode = Objects.requireNonNull(zipCode);
        this.city = Objects.requireNonNull(city);
        this.street = Objects.requireNonNull(street);
    }

    @Override
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getStreet() {
        return street;
    }
}
