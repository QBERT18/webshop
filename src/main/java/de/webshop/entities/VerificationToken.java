package de.webshop.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "VERIFICATION_TOKEN")
public class VerificationToken extends AbstractDbEntity<VerificationToken> {

    private static final int EXPIRATION = 60 * 24; // why is this unused?

    /*
     * RELATIONS
     */

    @Id
    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "USER_ID", unique = true, nullable = false, updatable = false)
    private long userId;

    // why is the token in an extra field and not in the id field? the id field is useless for a one-to-one relation
    @Column(name = "TOKEN", unique = true, nullable = false, updatable = false)
    private String token;

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "EXPIRY_DATE", nullable = false, updatable = false)
    private LocalDateTime expiryDate;

    /**
     * VerificationToken constructor.
     *
     * @param userId the userid this token belongs to
     * @param token  the token
     */
    public VerificationToken(long userId, String token) {
        this.userId = userId;
        this.token = token;
        this.createdDate = LocalDateTime.now();
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    /**
     * empty Constructor for JPA
     */
    public VerificationToken() {
    }

    @Override
    public VerificationToken deepCopy() {
        final VerificationToken copy = new VerificationToken();
        copy.userId = userId;
        copy.token = token;
        copy.createdDate = createdDate != null ? LocalDateTime.from(createdDate) : null;
        copy.expiryDate = expiryDate != null ? LocalDateTime.from(expiryDate) : null;
        return copy;
    }

    private static LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) { // why is this unused?
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreated() {
        return createdDate;
    }

    public void setCreated(LocalDateTime createdDate) {
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
        return Objects.equals(token, that.token) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, userId, createdDate, expiryDate);
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                ", token='" + token + '\'' +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}