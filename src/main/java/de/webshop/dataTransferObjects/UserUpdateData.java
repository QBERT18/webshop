package de.webshop.dataTransferObjects;

import java.util.Objects;

public class UserUpdateData {

    private String email;

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserUpdateData that = (UserUpdateData) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "UserUpdateData{" +
                "email='" + email + '\'' +
                '}';
    }
}
