package de.webshop.model;

public interface User {

    /**
     * @return the user id
     */
    long getUserId();

    /**
     * @return the users mail address
     */
    String getEmail();

    /**
     * @return the users first name
     */
    String getFirstName();

    /**
     * @return the users last name
     */
    String getLastName();

    /**
     * @return the SHA-256 hash of users password
     */
    String getPasswordHash();

    /**
     * @return the salt used for hashing the password
     */
    String getPasswordSalt();

    /**
     * @return the permission the user has
     */
    UserPermission getUserPermission();

}
