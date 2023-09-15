package com.ceraphi.controller.LoginController;

import com.ceraphi.dto.LoginDto;
import com.ceraphi.dto.SignUpDto;
import com.ceraphi.entities.Role;
import com.ceraphi.entities.User;
import com.ceraphi.repository.RoleRepository;
import com.ceraphi.repository.UserRepository;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.AuthResponse;
import com.ceraphi.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping({"/api/auth"})
public class AuthController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;



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
                if(user==null){
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
//            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
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

}



