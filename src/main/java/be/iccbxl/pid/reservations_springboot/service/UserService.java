
package be.iccbxl.pid.reservations_springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservations_springboot.dto.UserProfileDto;
import be.iccbxl.pid.reservations_springboot.dto.UserRegistrationDto;
import be.iccbxl.pid.reservations_springboot.model.Role;
import be.iccbxl.pid.reservations_springboot.model.RoleRepository;
import be.iccbxl.pid.reservations_springboot.model.User;
import be.iccbxl.pid.reservations_springboot.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isLoginAndEmailAvailable(String login, String email) {
        return !userRepository.existsByLogin(login)
                && !userRepository.existsByEmail(email);
    }

    @Transactional
    public void registerFromDto(UserRegistrationDto dto) {

        User user = new User();

        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setLogin(dto.getLogin());
        user.setEmail(dto.getEmail());
        user.setLangue(dto.getLangue());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Récupérer le rôle MEMBER
        Role memberRole = roleRepository.findByRole("MEMBER");

        if (memberRole == null) {
            throw new IllegalStateException(
                "Le rôle MEMBER est introuvable dans la base de données"
            );
        }

        // Enregistrer le nouvel utilisateur
        userRepository.save(user);

        // Associer le rôle à l'utilisateur
        user.addRole(memberRole);

        // Enregistrer le côté propriétaire de la relation
        roleRepository.save(memberRole);
    }

    public void updateUser(long id, User user) {
        userRepository.save(user);
    }

    public void updateUserFromDto(UserProfileDto dto) {

        User user = userRepository.findById(dto.getId())
                .orElseThrow(() ->
                    new RuntimeException("Utilisateur introuvable")
                );

        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setLangue(dto.getLangue());

        // Modifier le mot de passe uniquement s'il est renseigné
        if (dto.getPassword() != null
                && !dto.getPassword().isBlank()) {

            user.setPassword(
                passwordEncoder.encode(dto.getPassword())
            );
        }

        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void deleteByLogin(String login) {
        User user = userRepository.findByLogin(login);
        userRepository.delete(user);
    }
}
