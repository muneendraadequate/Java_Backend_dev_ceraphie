package com.ceraphi.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class PasswordResetToken {
    private static final int EXPIRATION_TIME_IN_MINUTES = 60 * 24; // 24 hours

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private String token;

    private LocalDateTime expiryDate;
    private LocalDateTime creationDateTime;
    public PasswordResetToken() {
        this.expiryDate = calculateExpiryDate();
    }


    public PasswordResetToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.creationDateTime = LocalDateTime.now();
        this.expiryDate = calculateExpiryDate();
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTES);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);

    }


}