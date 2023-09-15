package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Random;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_details")
@Where(clause = "is_deleted = false")
public class ClientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String clientName;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    @Column(name = "client_type")
    private String clientType;

    @Column(name = "email")
    private String email;

    @Column(name = "language")
    private String language;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "local_currency")
    private String localCurrency;

    @Column(name = "restriction")
    private boolean restriction;

    @Column(name = "geopolitical_data")
    private String geopoliticalData;

    @Column(name = "restriction_details")
    private String restrictionDetails;

}