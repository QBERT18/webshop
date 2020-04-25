package de.webshop.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String userPermission;

    /**
     * User Constructor.
     *
     * @param email    Email of User.
     * @param password Hashed Password of User.
     * @throws NullPointerException When either email, password or displayName is null.
     */
    public User(@NotNull final String email, final @NotNull String password) throws NullPointerException {
        if (email == null || password == null) {
            throw new NullPointerException();
        }
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

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
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
        return userID == user.userID &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                userPermission.equals(user.userPermission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, email, password, firstName, lastName, userPermission);
    }
}
