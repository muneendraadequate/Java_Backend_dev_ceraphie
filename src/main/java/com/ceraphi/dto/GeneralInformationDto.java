package com.ceraphi.dto;

import com.ceraphi.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformationDto {
    @NotEmpty(message = "projectName couldn't  be empty")
    @Size(min = 2, message = "Project name   should have at least 2 characters")
    private String projectName;
    @NotEmpty(message = "address field couldn't be empty ")
    private String address;
    @NotEmpty(message = "county is missing ")
    private String country;
    @NotEmpty(message = "preferredUnits field couldn't  be empty")
    private String preferredUnits;
    @NotEmpty(message = "clientName field couldn't be empty ")
    private String clientName;
    @NotEmpty(message = "select the city")
    private String city;
    @NotEmpty(message = "postalCode is missing")
    private String postalCode;
    @NotEmpty
    @Size(min = 5, message = "geoPoliticalData field should contains minimum 20 characters ")
    private String GeoPoliticalData;
    @NotEmpty(message = "restrictionDetails field couldn't be empty")
    @Size(min = 5, message = "restrictionDetails field should contains minimum 20 characters ")
    private String restrictionDetails;
    @NotEmpty(message = "clientId field must have clientId")
    private String clientId;
    @NotEmpty(message = "you have forgotten to select currency")
    private String projectCurrency;
    private Status status;
    private Long user;
    private Long key;
    private String ProjectType;

    public GeneralInformationDto(String projectName, String address, String country, String preferredUnits, String clientName, String city, String postalCode, String geoPoliticalData, String restrictionDetails, String clientId, String projectCurrency) {
        this.projectName = projectName;
        this.address = address;
        this.country = country;
        this.preferredUnits = preferredUnits;
        this.clientName = clientName;
        this.city = city;
        this.postalCode = postalCode;
        this.GeoPoliticalData = geoPoliticalData;
        this.restrictionDetails = restrictionDetails;
        this.clientId = clientId;
        this.projectCurrency = projectCurrency;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getPreferredUnits() {
        return preferredUnits;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getGeoPoliticalData() {
        return GeoPoliticalData;
    }

    public String getRestrictionDetails() {
        return restrictionDetails;
    }

    public String getClientId() {
        return clientId;
    }

    public String getProjectCurrency() {
        return projectCurrency;
    }
}
