package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatCapex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Site_preparation_civil_engineering;
    private String Drilling_unit_mob_demob;
    private String Borehole_drilling_construction;
    private String Borehole_completion;
    private String Heat_pump_heat_exchanger_installation;
    private String Heat_connection;
    private String Plate_heat_exchanger;
    private double  Total;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;

}
