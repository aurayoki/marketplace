package com.jm.marketplace.password.reset.service;


import com.jm.marketplace.password.reset.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(String email, String token);

   Optional <PasswordResetToken> getVerificationToken(String token);

    void deletePasswordResetToken(String email);
}
