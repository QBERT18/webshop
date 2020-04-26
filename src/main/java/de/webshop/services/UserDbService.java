package de.webshop.services;

import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.dataTransferObjects.UserUpdateData;
import de.webshop.entities.User;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

public interface UserDbService {

    Iterable<User> getAllUsers();

    Optional<User> getUserByEmail(String email);

    User registerNewUser(RegistrationData registrationData) throws DuplicateKeyException;

    /**
     *
     * @param user the old user
     * @param userUpdateData the data to change
     * @return the updated user
     */
    User updateUser(User user, UserUpdateData userUpdateData);
}
