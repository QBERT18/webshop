package de.webshop.entities;

import de.webshop.util.DeepCopyUtility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "VERIFICATION_TOKEN")
public class VerificationToken extends AbstractDbEntity<VerificationToken> {

    private static final int EXPIRATION = 60 * 24; // why is this unused?


    /*
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_ID", unique = true, nullable = false, updatable = false)
    private int id;


    /*
     * RELATIONS
     */

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private User user;

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
     * @param user  the user this token belongs to
     * @param token the token
     */
    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    /**
     * empty Constructor for JPA
     */
    public VerificationToken() {
    }

    @Override
    public VerificationToken deepCopy() {
        final VerificationToken copy = new VerificationToken();
        copy.id = id;
        copy.user = DeepCopyUtility.nullSafeDeepCopy(user);
        copy.token = token;
        copy.createdDate = createdDate != null ? LocalDateTime.from(createdDate) : null;
        copy.expiryDate = expiryDate != null ? LocalDateTime.from(expiryDate) : null;
        return copy;
    }

    private static Date calculateExpiryDate(int expiryTimeInMinutes) { // why is this unused?
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return id == that.id &&
                Objects.equals(token, that.token) &&
                Objects.equals(user, that.user) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, user, createdDate, expiryDate);
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", createdDate=" + createdDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}