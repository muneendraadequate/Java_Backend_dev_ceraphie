package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatOpex {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String WellMaintenance;
    private String Heat_pump_electrical_consumption;
    private String Pumping_power_consumption;
    private double  totalOpex;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;
}
