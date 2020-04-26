package de.webshop.services;

import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.dataTransferObjects.UserUpdateData;
import de.webshop.entities.User;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

public interface UserDbService {

    Iterable<User> getAllUsers() throws UserDbServiceException;

    Optional<User> getUserByEmail(String email) throws UserDbServiceException;

    User registerNewUser(RegistrationData registrationData) throws DuplicateKeyException, UserDbServiceException;

    /**
     * @param user           the old user
     * @param userUpdateData the data to change
     * @return the updated user
     */
    User updateUser(User user, UserUpdateData userUpdateData) throws UserDbServiceException;
}
