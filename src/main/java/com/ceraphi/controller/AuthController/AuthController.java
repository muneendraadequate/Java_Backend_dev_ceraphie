package com.ceraphi.controller.AuthController;

import com.ceraphi.dto.LoginDto;
import com.ceraphi.dto.SignUpDto;
import com.ceraphi.entities.Role;
import com.ceraphi.entities.User;
import com.ceraphi.repository.PasswordResetTokenRepository;
import com.ceraphi.repository.RoleRepository;
import com.ceraphi.repository.UserRepository;
import com.ceraphi.utils.*;
import com.ceraphi.utils.authUtils.EmailService;
import com.ceraphi.utils.authUtils.PasswordResetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(value = "*",allowedHeaders = "*")
public class AuthController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private  EmailService emailService;

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Validation error")
                    .errors(fieldErrors)
                    .build();
            return ResponseEntity.ok(apiResponseData);
        } else {
            try {
                User user = userRepository.getByUsername(loginDto.getUsernameOrEmail());

                if(user==null) {
                    AuthResponse<?> errorResponse = AuthResponse.builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Invalid username or password")
                            .build();
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
                } else if (!user.getPassword().equals(loginDto.getPassword())) {
                    AuthResponse<?> errorResponse = AuthResponse.builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Invalid username or password")
                            .build();
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);


            }else {
                    AuthResponse<?> apiResponseData = AuthResponse.builder()
                            .status(HttpStatus.OK.value())
                            .data(Response.builder().id(user.getId()).username(user.getUsername()).build())
                            .build();
                    return ResponseEntity.ok(apiResponseData);
                }
            } catch (Exception e) {
                AuthResponse<?> errorResponse = AuthResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("Invalid username or password")
                        .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
            }
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Validation error")
                    .errors(fieldErrors)
                    .build();
            return ResponseEntity.ok(apiResponseData);
        } else {
            // add check for username exists in a DB
            if (userRepository.existsByUsername(signUpDto.getUsername())) {
                AuthResponse<?> apiResponseData = AuthResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("Username is already taken!")
                        .build();
                return ResponseEntity.ok(apiResponseData);
            }

            // add check for email exists in DB
            if (userRepository.existsByUsername(signUpDto.getUsername())) {
                AuthResponse<?> apiResponseData = AuthResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("Email is already taken!")
                        .build();
                return ResponseEntity.ok(apiResponseData);
            }

            // create user object
            User user = new User();
            user.setType(signUpDto.getType());
            user.setLastName(signUpDto.getLastName());
            user.setFirstName(signUpDto.getFirstName());
            user.setClientId(signUpDto.getClientId());
            user.setUsername(signUpDto.getUsername());
            user.setPassword(signUpDto.getPassword());
            Role roles = roleRepository.findByName("ROLE_ADMIN").get();
            user.setUserRoles(Collections.singleton(roles));
            user.setRole(roles.getName().toString());

            userRepository.save(user);

            AuthResponse<?> apiResponseData = AuthResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("user is registered successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }


    public void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }



    @PostMapping(
            path = {"/forgot-password"},
            produces = {"application/json"}
    )
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordRequest request) throws Exception {
        User user = this.userRepository.findByUsername(request.getEmail());
        if (user == null) {
            AuthResponse<?> apiResponseData = AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Email address not found")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        } else {
            long userId = user.getId();
            String encryptedText = encrypt(String.valueOf(userId), "chandan");
            try
            {
                encryptedText = URLEncoder.encode(encryptedText, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                AuthResponse<?> apiResponseData = AuthResponse.builder()
                        .status(HttpStatus.LENGTH_REQUIRED.value())
                        .message("Failed to generate reset link")
                        .build();
                return ResponseEntity.ok(apiResponseData);
            }
            String resetLink = "http://ceraphi.adequateshop.com/verification?userId=" + encryptedText;
            this.emailService.sendResetPasswordEmail(user.getUsername(), resetLink,"Here you can reset your password");
            AuthResponse<?> apiResponseData = AuthResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Reset link sent to your email")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }

    @PutMapping("/reset")
    public ResponseEntity<?> resetUserPassword(@RequestBody PasswordResetRequest request) {
        String decryptedText="";
        try {
            decryptedText = decrypt(request.getUserId(), "chandan");
            System.out.println("Decrypted Text After Decryption: " + decryptedText);
        }catch (Exception e){
            AuthResponse<?> apiResponseData = AuthResponse.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("User does not exist")
            .build();
            return ResponseEntity.ok(apiResponseData);
        }

        Optional<User> byId = userRepository.findById(Long.valueOf(decryptedText));

        if (byId.isPresent()) {
            User user = byId.get();
            if (user.getId()==(Long.valueOf(decryptedText))) {
                user.setPassword(request.getPassword());
                this.userRepository.save(user);

                AuthResponse<?> apiResponseData = AuthResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("User password updated successfully")
                        .build();
                return ResponseEntity.ok(apiResponseData);
            } else {
                AuthResponse<?> apiResponseData = AuthResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("User not found or password not updated")
                        .build();
                return ResponseEntity.ok(apiResponseData);
            }
        } else {
            AuthResponse<?> apiResponseData = AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User not found or password not updated")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }

}






