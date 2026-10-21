package be.iccbxl.pid.reservations_springboot.dto;

import be.iccbxl.pid.reservations_springboot.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatches  // annotation personnalisée
public class UserRegistrationDto {
    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;

    @NotBlank(message = "Le login est obligatoire")
    @Size(min = 4, max = 20, message = "Le login doit contenir entre 4 et 20 caractères")
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    @NotBlank(message = "Veuillez confirmer votre mot de passe")
    private String confirmPassword;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "La langue est obligatoire")
    private String langue;
    public String getLogin() {
    return login;
}

public String getEmail() {
    return email;
}

public String getPassword() {
    return password;
}

public String getConfirmPassword() {
    return confirmPassword;
}

public String getFirstname() {
    return firstname;
}


public void setFirstname(String firstname) {
    this.firstname = firstname;
}

public String getLastname() {
    return lastname;
}

public void setLastname(String lastname) {
    this.lastname = lastname;
}



public void setLogin(String login) {
    this.login = login;
}



public void setPassword(String password) {
    this.password = password;
}



public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
}


public void setEmail(String email) {
    this.email = email;
}

public String getLangue() {
    return langue;
}

public void setLangue(String langue) {
    this.langue = langue;
}

    
}
