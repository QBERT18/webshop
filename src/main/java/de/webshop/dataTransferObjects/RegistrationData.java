package de.webshop.dataTransferObjects;

import de.webshop.util.StringUtilities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class RegistrationData implements DataTransferObject {

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String countryCode;

    @NotNull
    @NotEmpty
    private String zipCode;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String street;

    @Override
    public boolean isValid() {
        return StringUtilities.isAnyNullOrEmpty(email, password, firstName, lastName, countryCode, zipCode, city, street);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistrationData data = (RegistrationData) o;
        return Objects.equals(email, data.email) &&
                Objects.equals(password, data.password) &&
                Objects.equals(firstName, data.firstName) &&
                Objects.equals(lastName, data.lastName) &&
                Objects.equals(countryCode, data.countryCode) &&
                Objects.equals(zipCode, data.zipCode) &&
                Objects.equals(city, data.city) &&
                Objects.equals(street, data.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, firstName, lastName, countryCode, zipCode, city, street);
    }
}
