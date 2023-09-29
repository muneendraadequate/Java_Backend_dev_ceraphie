package com.ceraphi.utils.authUtils;

import com.ceraphi.entities.PasswordResetToken;
import com.ceraphi.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PasswordResetTokenExpirationJob {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenExpirationJob(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Scheduled(cron = "0 0 * * *") // Run once every hour
    public void removeExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        List<PasswordResetToken> expiredTokens = passwordResetTokenRepository.findByExpiryDateBefore(now);
        passwordResetTokenRepository.deleteAll(expiredTokens);
    }
}