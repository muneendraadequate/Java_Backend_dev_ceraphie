package com.ceraphi.utils.authUtils;

public interface EmailService {
    void sendResetPasswordEmail(String email, String resetLink, String s);
    void userRegistrationEmail(String email, String password);
}
