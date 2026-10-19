package be.iccbxl.pid.reservations_springboot.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @SuppressWarnings("unused") // Read and managed by JPA.
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    @SuppressWarnings("unused") // Read by the authentication framework.
    private String password;

    private String firstname;
    private String lastname;

    @Column(unique = true, nullable = false)
    @SuppressWarnings("unused") // Read by the authentication framework.
    private String email;
    @SuppressWarnings("unused") // Read by the authentication framework.
    private String langue;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    @SuppressWarnings({"unused", "FieldMayBeFinal"}) // Managed by JPA.
    private LocalDateTime created_at = LocalDateTime.now();

    @Override
    public String toString() {
        return login + " (" + firstname + " " + lastname + " - " + role + ")";
    }
    public Long getId() {
    return id;
}
}
