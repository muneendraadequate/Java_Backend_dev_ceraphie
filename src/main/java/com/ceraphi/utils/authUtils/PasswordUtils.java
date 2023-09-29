package com.ceraphi.utils.authUtils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class PasswordUtils {
    private static final int RESET_TOKEN_EXPIRATION_HOURS = 24*60;

    public PasswordUtils() {
    }

    public static String generateResetToken() {

        return UUID.randomUUID().toString();
    }

    public static LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plus(24L, ChronoUnit.HOURS);
    }
}
