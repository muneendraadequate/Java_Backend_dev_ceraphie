package com.ceraphi.repository;


import com.ceraphi.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    List<PasswordResetToken> findByExpiryDateBefore(LocalDateTime now);
}