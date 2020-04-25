package de.webshop.services.impl;

import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.dataTransferObjects.UserUpdateData;
import de.webshop.db.dataAccessObjects.UserRepository;
import de.webshop.entitites.User;
import de.webshop.services.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
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
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User registerNewUser(RegistrationData registrationData) throws DuplicateKeyException {
        final String email = registrationData.getEmail();
        if (userRepository.getUserByEmail(email) != null) {
            throw new DuplicateKeyException("User with this email already exists: " + email);
        }
        final String password = passwordEncoder.encode(registrationData.getPassword());
        return userRepository.save(new User(email, password));
    }

    @Override
    public User updateUser(User user, UserUpdateData userUpdateData) {
        user.setEmail(userUpdateData.getEmail());
        return user;
    }
}
