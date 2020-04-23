package de.webshop.model;

public class UserImpl implements User {

    private final long userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;
    private final String passwordSalt;
    private final UserPermission userPermission;

    /**
     * @param userId         > 0
     * @param email          must be non-null
     * @param firstName      must be non-null
     * @param lastName       must be non-null
     * @param passwordHash   must be non-null
     * @param passwordSalt   must be non-null
     * @param userPermission must be non-null
     * @throws IllegalArgumentException if id <= 0 or any argument is null
     */
    public UserImpl(final long userId, final String email, final String firstName, final String lastName, final String passwordHash, final String passwordSalt, final UserPermission userPermission) {
        if (userId <= 0 || email == null || firstName == null || lastName == null || passwordHash == null || passwordSalt == null || userPermission == null) {
            throw new IllegalArgumentException("Any argument was null or id <= 0");
        } else {
            this.userId = userId;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.passwordHash = passwordHash;
            this.passwordSalt = passwordSalt;
            this.userPermission = userPermission;
        }
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String getPasswordSalt() {
        return passwordSalt;
    }

    @Override
    public UserPermission getUserPermission() {
        return userPermission;
    }
}
