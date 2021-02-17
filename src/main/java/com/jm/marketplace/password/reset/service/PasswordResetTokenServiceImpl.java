package com.jm.marketplace.password.reset.service;


import com.jm.marketplace.password.reset.dao.PasswordResetTokenRepository;
import com.jm.marketplace.password.reset.model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PasswordResetTokenService")
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public void createPasswordResetTokenForUser(String email, String token) {
        final PasswordResetToken passwordResetToken = new PasswordResetToken(token, email);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken getVerificationToken(String token) {
        return passwordResetTokenRepository.findPasswordResetTokenByToken(token);
    }

    @Override
    public void deletePasswordResetToken(String email) {
        passwordResetTokenRepository.deleteByEmail(email);
    }
}
