package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @NotBlank(message = "firstName field can't be empty")
    private String firstName;
    @NotBlank(message = "lastName field can't be empty")
    private String surName;
    @NotBlank(message = "email field can't be empty")
    @Email
    private String email;
    private String role ;

}