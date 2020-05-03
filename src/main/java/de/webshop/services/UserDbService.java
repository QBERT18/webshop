package de.webshop.services;

import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.entities.User;
import de.webshop.entities.VerificationToken;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

public interface UserDbService {

    /**
     * Get all users currently in the database.
     *
     * @return all users
     * @throws UserDbServiceException if database access fails
     */
    Iterable<User> getAllUsers() throws UserDbServiceException;

    /**
     * Searches for an user by email address.
     *
     * @param email the email to search for
     * @return the user if found, otherwise empty
     * @throws UserDbServiceException if database access fails
     */
    Optional<User> getUserByEmail(String email) throws UserDbServiceException;

    /**
     * Searches for an user by userId.
     *
     * @param userId the user id
     * @return the user with the given userId or empty if not found
     * @throws UserDbServiceException if database access fails
     */
    Optional<User> getUserById(long userId) throws UserDbServiceException;


    Optional<User> getUserByToken(String token) throws UserDbServiceException;

    /**
     * Creates a new user from the given registration data and puts him in the database.
     *
     * @param registrationData the data to use when constructing the new user
     * @return the added user
     * @throws DuplicateKeyException  if email is already registered
     * @throws UserDbServiceException if database access fails
     */
    User registerNewUser(RegistrationData registrationData) throws DuplicateKeyException, UserDbServiceException;

    /**
     * @param oldUser the old user
     * @param newUser the new user
     * @return the updated user
     * @throws UserDbServiceException if userIDs don't match or database access fails
     */
    User updateUser(User oldUser, User newUser) throws UserDbServiceException;

    /**
     * @param userId the id of the user who wants to verify his account
     * @throws UserDbServiceException if database access fails
     */
    public VerificationToken createVerificationToken(long userId, String token) throws UserDbServiceException;

}
