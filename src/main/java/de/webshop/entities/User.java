package de.webshop.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

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

    @Column(name = "USER_PERMISSION", nullable = false, length = 256)
    private String userPermission;

    /**
     * User Constructor.
     *
     * @param email    Email of User. not-null
     * @param password Hashed Password of User. not-null
     */
    public User(@NotNull final String email, final @NotNull String password) throws NullPointerException {
        this.email = email;
        this.password = password;
    }

    /**
     * empty Constructor for JPA
     */
    public User() {
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

    public String getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(String userPermission) {
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
                email.equals(user.email) &&
                password.equals(user.password) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                userPermission.equals(user.userPermission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orders, deliveryAddress, billAddress, email, password, firstName, lastName, userPermission);
    }
}
