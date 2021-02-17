package com.jm.marketplace.password.reset.service;


import com.jm.marketplace.password.reset.model.PasswordResetToken;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(String email, String token);

    PasswordResetToken getVerificationToken(String token);

    void deletePasswordResetToken(String email);
}
