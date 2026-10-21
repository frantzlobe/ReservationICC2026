package be.iccbxl.pid.reservations_springboot.dto;

import be.iccbxl.pid.reservations_springboot.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordMatches(message = "Les mots de passe ne correspondent pas")
public class UserProfileDto {

    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "La langue est obligatoire")
    private String langue;

    private String login; // visible mais non modifiable
    private String role;  // visible mais non modifiable

    private String password; // nouveau mot de passe (optionnel)

    private String confirmPassword; // confirmation du mot de passe

    
public void setFirstname(String firstname) {
    this.firstname = firstname;
}

public void setLastname(String lastname) {
    this.lastname = lastname;
}

public void setEmail(String email) {
    this.email = email;
}

public void setLangue(String langue) {
    this.langue = langue;
}

public void setLogin(String login) {
    this.login = login;
}

public void setRole(String role) {
    this.role = role;
}

public void setPassword(String password) {
    this.password = password;
}

public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
}

public void setId(Long id) {
    this.id = id;
}

public Long getId() {
    return id;
}

public String getFirstname() {
    return firstname;
}

public String getLastname() {
    return lastname;
}

public String getEmail() {
    return email;
}

public String getLangue() {
    return langue;
}

public String getPassword() {
    return password;
}
}
