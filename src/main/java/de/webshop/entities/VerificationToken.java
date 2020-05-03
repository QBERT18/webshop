package de.webshop.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "VERIFICATION_TOKEN")
public class VerificationToken extends AbstractDbEntity<VerificationToken> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int EXPIRATION_TIME_IN_MINUTES = 60 * 24;

    /*
     * ID
     */

    @Id
    @Column(name = "USER_ID", nullable = false)
    private long userId;

    /*
     * RELATIONS
     */

    @OneToOne
    @MapsId
    private User user;

    /*
     * FIELDS
     */

    @Column(name = "TOKEN", unique = true, nullable = false, updatable = false)
    private String token;

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "EXPIRY_DATE", nullable = false, updatable = false)
    private LocalDateTime expiryDate;

    /**
     * VerificationToken constructor.
     *
     * @param token the token
     */
    public VerificationToken(long userId, String token) {
        this.userId = userId;
        this.token = token;
        createdDate = LocalDateTime.now();
        expiryDate = createdDate.plusMinutes(EXPIRATION_TIME_IN_MINUTES);
    }

    /**
     * empty Constructor for JPA
     */
    public VerificationToken() {
    }

    @Override
    public VerificationToken deepCopy() {
        final VerificationToken copy = new VerificationToken();
        copy.user = user;
        copy.token = token;
        copy.createdDate = createdDate != null ? LocalDateTime.from(createdDate) : null;
        copy.expiryDate = expiryDate != null ? LocalDateTime.from(expiryDate) : null;
        return copy;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static int getExpirationTimeInMinutes() {
        return EXPIRATION_TIME_IN_MINUTES;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VerificationToken that = (VerificationToken) o;
        return userId == that.userId &&
                Objects.equals(token, that.token) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, token, createdDate, expiryDate);
    }
}