package de.webshop.services.impl;

import de.webshop.constants.UserPermission;
import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.dataTransferObjects.UserUpdateData;
import de.webshop.db.dataAccessObjects.UserRepository;
import de.webshop.entities.Address;
import de.webshop.entities.User;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDbService")
public class UserDbServiceImpl implements UserDbService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDbServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.getUserByEmail(email));
    }

    @Override
    public User registerNewUser(RegistrationData registrationData) throws DuplicateKeyException, UserDbServiceException {
        if (registrationData == null || !registrationData.isValid()) {
            throw new UserDbServiceException("Invalid or null registrationData DTO");
        } else {
            final String email = registrationData.getEmail();
            if (userRepository.getUserByEmail(email) != null) {
                throw new DuplicateKeyException("User with this email already exists: " + email);
            }
            final String password = passwordEncoder.encode(registrationData.getPassword());
            final String firstName = registrationData.getFirstName();
            final String lastName = registrationData.getLastName();
            final Address deliverAddress = new Address(registrationData.getCountryCode(), registrationData.getZipCode(),
                    registrationData.getCity(), registrationData.getStreet());
            final User newUser = new User(email, password, firstName, lastName, deliverAddress, null, UserPermission.RESTRICTED);
            return userRepository.save(newUser);
        }
    }

    @Override
    public User updateUser(User user, UserUpdateData userUpdateData) {
        if (!user.getEmail().equals(userUpdateData.getEmail())) {
            user.setEmail(userUpdateData.getEmail());
        }
        return user;
    }
}
