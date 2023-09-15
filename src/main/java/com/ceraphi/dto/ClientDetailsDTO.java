package com.ceraphi.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ClientDetailsDTO {

    @NotBlank(message = "ClientName is required")
    private String clientName;
    @NotNull(message = " ClientType is required")
    private String clientType;
    @NotEmpty(message = "email fields Can't be empty")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "language fields Can't be empty")
    private String language;
    @NotEmpty(message = "address fields Can't be empty")
    private String address;
    @NotEmpty(message = "city fields Can't be empty")
    private String city;
    @NotEmpty(message = "country fields Can't be empty")
    private String country;
    @NotEmpty(message = "postalCode fields Can't be empty")
    private String postalCode;
    @NotEmpty(message = "localCurrency fields Can't be empty")
    private String localCurrency;
    private boolean restriction;
    @NotEmpty(message = "geopoliticalData fields Can't be empty")
    private String geopoliticalData;
    @NotEmpty(message = "restrictionDetails fields Can't be empty")
    private String restrictionDetails;
    private Long clientKey;
    private Long userId;
}