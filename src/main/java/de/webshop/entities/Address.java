package de.webshop.entities;

import de.webshop.dataTransferObjects.AddressData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ADDRESSES")
public class Address {

    /*
     * ID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID", unique = true, nullable = false, updatable = false)
    private long addressId;

    /*
     * RELATIONS
     */

    @OneToMany(mappedBy = "deliveryAddress", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<User> usersWithThisDeliveryAddress;

    @OneToMany(mappedBy = "billAddress", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<User> usersWithThisBillAddress;

    /*
     * FIELDS
     */

    @Column(name = "COUNTRY_CODE", nullable = false)
    private String countryCode;

    @Column(name = "ZIP_CODE", nullable = false)
    private String zipCode;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STREET", nullable = false)
    private String street;

    /**
     * Address constructor
     *
     * @param countryCode non-null, non-empty
     * @param zipCode     non-null, non-empty
     * @param city        non-null, non-empty
     * @param street      non-null, non-empty
     */
    public Address(@NotNull @NotEmpty String countryCode, @NotNull @NotEmpty String zipCode, @NotNull @NotEmpty String city, @NotNull @NotEmpty String street) {
        this.countryCode = countryCode;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
    }

    /**
     * empty Constructor for JPA
     */
    public Address() {
    }

    // TODO move this into some kind of factory and declare a checked custom Exception
    public static Address from(final AddressData addressData) throws IllegalArgumentException {
        if (addressData == null || !addressData.isValid()) {
            throw new IllegalArgumentException("AddressData was null or invalid: " + addressData);
        } else {
            return new Address(addressData.getCountryCode(), addressData.getZipCode(), addressData.getCity(), addressData.getStreet());
        }
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<User> getUsersDeliveryAddresses() {
        return usersWithThisDeliveryAddress;
    }

    public void setUsersDeliveryAddresses(List<User> usersWithThisDeliveryAddress) {
        this.usersWithThisDeliveryAddress = usersWithThisDeliveryAddress;
    }

    public List<User> getUsersBillAddresses() {
        return usersWithThisBillAddress;
    }

    public void setUsersBillAddresses(List<User> usersWithThisBillAddress) {
        this.usersWithThisBillAddress = usersWithThisBillAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return addressId == address.addressId &&
                Objects.equals(usersWithThisDeliveryAddress, address.usersWithThisDeliveryAddress) &&
                Objects.equals(usersWithThisBillAddress, address.usersWithThisBillAddress) &&
                countryCode.equals(address.countryCode) &&
                zipCode.equals(address.zipCode) &&
                city.equals(address.city) &&
                street.equals(address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, usersWithThisDeliveryAddress, usersWithThisBillAddress, countryCode, zipCode, city, street);
    }
}
