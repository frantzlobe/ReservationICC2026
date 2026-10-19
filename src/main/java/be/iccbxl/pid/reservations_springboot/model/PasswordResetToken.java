package be.iccbxl.pid.reservations_springboot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="personal_access_tokens")
public class PasswordResetToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("unused") // Read by JPA through reflection.
    private Long id;

    @Column(unique = true, nullable = false)
    @SuppressWarnings("unused") // Read by JPA through reflection.
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @SuppressWarnings("unused") // Read by JPA through reflection.
    private User user;

    @Column(name = "expires_at")
    private LocalDateTime expiryDate;

    public PasswordResetToken() {
    }

    public PasswordResetToken(Long id, String token, User user, LocalDateTime expiryDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
