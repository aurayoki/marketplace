package com.jm.marketplace.password.reset.dao;

import com.jm.marketplace.password.reset.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {


    PasswordResetToken findPasswordResetTokenByToken(String token);

    @Modifying
    @Query("delete from PasswordResetToken t where t.email=:email")
    void deleteByEmail(String email);
}
