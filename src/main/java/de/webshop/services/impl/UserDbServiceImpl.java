package de.webshop.services.impl;

import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.db.dataAccessObjects.TokenRepository;
import de.webshop.db.dataAccessObjects.UserRepository;
import de.webshop.entities.Address;
import de.webshop.entities.User;
import de.webshop.entities.VerificationToken;
import de.webshop.services.AddressDbService;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.AddressDbServiceException;
import de.webshop.services.exceptions.UserDbServiceException;
import de.webshop.util.StringUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service("userDbService")
public class UserDbServiceImpl implements UserDbService {

    private static final int VERIFICATION_TOKEN_EXPIRY_TIME_IN_HOURS = 24;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressDbService addressDbService;

    @Autowired
    public UserDbServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressDbService addressDbService, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressDbService = addressDbService;
        this.tokenRepository = tokenRepository;
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
    public Optional<User> getUserById(final long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByToken(String token) throws UserDbServiceException {
        if (StringUtilities.isNullOrEmpty(token)) {
            throw new UserDbServiceException("Token can't be null or empty!");
        }
        return Optional.ofNullable(userRepository.getUserByToken(token));
    }

    @Override
    public User registerNewUser(RegistrationData registrationData) throws DuplicateKeyException, UserDbServiceException {
        final String email = registrationData.getEmail();
        if (StringUtilities.isNullOrEmpty(email)) {
            throw new UserDbServiceException("Email was null or empty: " + registrationData);
        } else if (userRepository.getUserByEmail(email) != null) {
            throw new DuplicateKeyException("User with this email already exists: " + email);
        } else {
            try {
                // as deliveryAddress is a non-null FK column, the address must be in the db before saving the new user
                Address savedNewAddress = addressDbService.saveNewAddress(registrationData.getAddressData());
                final User newUser = User.from(registrationData, savedNewAddress, passwordEncoder);
                return userRepository.save(newUser);
            } catch (AddressDbServiceException e) {
                throw new UserDbServiceException("Could not save new address", e);
            }
        }
    }

    @Override
    public User updateUser(User oldUser, User newUser) throws UserDbServiceException {
        if (oldUser.getUserId() != newUser.getUserId()) {
            throw new UserDbServiceException("Tried to update user with data from an user with different userID: old user:"
                    + oldUser + ", new user: " + newUser);
        } else {
            return userRepository.save(newUser);
        }
    }

    @Override
    public VerificationToken createVerificationToken(long userId, String token) {
        VerificationToken newUserToken = new VerificationToken(userId, token);
        tokenRepository.save(newUserToken);
        return newUserToken;
    }
}
