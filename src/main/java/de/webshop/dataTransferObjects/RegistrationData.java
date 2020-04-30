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

    //@NotNull
    private AddressData addressData;

    @Override
    public boolean isValid() {
        return !StringUtilities.isAnyNullOrEmpty(email, password, firstName, lastName) &&
                addressData != null && addressData.isValid();
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
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
                Objects.equals(addressData, data.addressData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, firstName, lastName, addressData);
    }

    @Override
    public String toString() {
        return "RegistrationData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressData=" + addressData +
                '}';
    }
}
