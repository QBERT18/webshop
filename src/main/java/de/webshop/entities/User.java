package de.webshop.entities;


import de.webshop.constants.UserPermission;
import de.webshop.constants.converter.UserPermissionConverter;
import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.util.DeepCopyUtility;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User extends AbstractDbEntity<User> {

    /*
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false, updatable = false)
    private long userId;

    /*
     * RELATIONS
     */

    @OneToMany(mappedBy = "orderId")
    private Set<Order> orders;

    @ManyToOne
    @JoinColumn(name = "FK_DELIVERY_ADDRESS_ID", referencedColumnName = "ADDRESS_ID", nullable = false)
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "FK_BILL_ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    private Address billAddress;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private VerificationToken token;

    /*
     * FIELDS
     */

    @Column(name = "EMAIL", unique = true, nullable = false, length = 512)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 512)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false, length = 256)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 256)
    private String lastName;

    @Column(name = "USER_PERMISSION", nullable = false, length = 128)
    @Convert(converter = UserPermissionConverter.class)
    private UserPermission userPermission;

    @Column(name = "ENABLED")
    private Boolean enabled;

    /**
     * User Constructor.
     *
     * @param email           email of user. non-null & non-empty
     * @param password        hashed password of user. non-null & non-empty
     * @param firstName       first name. non-null & non-empty
     * @param lastName        last name. non-null & non-empty
     * @param deliveryAddress delivery address. non-null
     * @param billAddress     bill address. may be null
     * @param userPermission  the kind of permission the user has. non-null
     */
    public User(final @NotNull @NotEmpty String email, final @NotNull @NotEmpty String password,
                final @NotNull @NotEmpty String firstName, final @NotNull @NotEmpty String lastName,
                final @NotNull Address deliveryAddress, final Address billAddress,
                final @NotNull UserPermission userPermission, @NotNull final Boolean enabled) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = deliveryAddress;
        this.billAddress = billAddress;
        this.userPermission = userPermission;
        this.enabled = enabled;
    }

    /**
     * empty Constructor for JPA
     */
    public User() {
    }

    @Override
    public User deepCopy() {
        final User copy = new User();
        copy.userId = userId;
        copy.orders = DeepCopyUtility.bulkDeepCopy(orders);
        copy.deliveryAddress = DeepCopyUtility.nullSafeDeepCopy(deliveryAddress);
        copy.billAddress = DeepCopyUtility.nullSafeDeepCopy(billAddress);
        copy.email = email;
        copy.password = password;
        copy.firstName = firstName;
        copy.lastName = lastName;
        copy.userPermission = userPermission;
        copy.enabled = enabled;
        return copy;
    }

    // TODO move this into some kind of factory and declare a checked custom Exception
    public static User from(final RegistrationData registrationData, final Address deliveryAddress, final PasswordEncoder passwordEncoder) throws IllegalArgumentException {
        if (registrationData == null || !registrationData.isValid()) {
            throw new IllegalArgumentException("RegistrationData was null or invalid: " + registrationData);
        } else if (passwordEncoder == null) {
            throw new IllegalStateException("PasswordEncoder was not initialized");
        } else {
            final String password = passwordEncoder.encode(registrationData.getPassword());
            final Address newDeliveryAddress = deliveryAddress != null ? deliveryAddress : Address.from(registrationData.getAddressData());
            return new User(registrationData.getEmail(), password, registrationData.getFirstName(), registrationData.getLastName(),
                    newDeliveryAddress, null, UserPermission.RESTRICTED, false);
        }
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserPermission getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(UserPermission userPermission) {
        this.userPermission = userPermission;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Address getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(Address billAddress) {
        this.billAddress = billAddress;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(orders, user.orders) &&
                Objects.equals(deliveryAddress, user.deliveryAddress) &&
                Objects.equals(billAddress, user.billAddress) &&
                Objects.equals(token, user.token) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                userPermission == user.userPermission &&
                Objects.equals(enabled, user.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orders, deliveryAddress, billAddress, token, email, password, firstName, lastName, userPermission, enabled);
    }
}