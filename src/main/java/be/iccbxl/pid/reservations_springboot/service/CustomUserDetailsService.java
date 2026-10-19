package be.iccbxl.pid.reservations_springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import be.iccbxl.pid.reservations_springboot.model.User;
import be.iccbxl.pid.reservations_springboot.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(username);
		
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        
        return new org.springframework.security.core.userdetails.User(
        		username, 
        		getPassword(user), 
        		getGrantedAuthorities(getRole(user)));
    }

    private String getRole(User user) {
        for (Class<?> type = user.getClass(); type != null; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (field.getName().equalsIgnoreCase("role")) {
                    try {
                        field.setAccessible(true);
                        Object role = field.get(user);
                        if (role == null) {
                            throw new IllegalStateException("User role is null");
                        }
                        return role.toString();
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Unable to read user role", e);
                    }
                }
            }
        }
        throw new IllegalStateException("No role field found on User");
    }

    private String getPassword(User user) {
        for (Class<?> type = user.getClass(); type != null; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                String name = field.getName().toLowerCase();
                if (name.contains("password") || name.contains("passwd")
                        || name.equals("pwd") || name.contains("motdepasse")) {
                    try {
                        field.setAccessible(true);
                        return (String) field.get(user);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Unable to read user password", e);
                    }
                }
            }
        }
        throw new IllegalStateException("No password field found on User");
    }
	
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return authorities;
    }

}
