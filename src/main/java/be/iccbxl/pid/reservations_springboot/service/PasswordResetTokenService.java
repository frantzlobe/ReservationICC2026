package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.model.PasswordResetToken;
import be.iccbxl.pid.reservations_springboot.model.User;
import be.iccbxl.pid.reservations_springboot.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.lang.reflect.Field;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepo;

    public PasswordResetTokenService(PasswordResetTokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public PasswordResetToken createTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken();
        try {
            Field tokenField = PasswordResetToken.class.getDeclaredField("token");
            tokenField.setAccessible(true);
            tokenField.set(prt, token);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to set the password reset token", e);
        }
        try {
            Field userField = PasswordResetToken.class.getDeclaredField("user");
            userField.setAccessible(true);
            userField.set(prt, user);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to associate the password reset token with the user", e);
        }
        try {
            Field expiryDateField = PasswordResetToken.class.getDeclaredField("expiryDate");
            expiryDateField.setAccessible(true);
            expiryDateField.set(prt, LocalDateTime.now().plusHours(1));
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to set the password reset token expiry date", e);
        }
        return tokenRepo.save(prt);
    }

    public User validatePasswordResetToken(String token) {
        PasswordResetToken prt = tokenRepo.findByToken(token).orElse(null);
        if (prt == null || prt.isExpired()) {
            return null;
        }
        try {
            Field userField = PasswordResetToken.class.getDeclaredField("user");
            userField.setAccessible(true);
            return (User) userField.get(prt);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to retrieve the user associated with the password reset token", e);
        }
    }

    @Transactional
    public void deleteToken(String token) {
        tokenRepo.deleteByToken(token);
    }
}
