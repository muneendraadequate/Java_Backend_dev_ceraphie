package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DeepCapex {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String Site_preparation_civil_engineering;
    private  String Drilling_unit_mob_demob;
    private String Borehole_drilling_construction;
    private String  Well_completion;
    private String Mechanical_circulation_pump;
    private String Heat_exchanger_installation;
    private  String Heat_connection;
    private int  total;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;
}
