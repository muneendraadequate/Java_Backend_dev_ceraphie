package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepCapexDto {
    private String Site_preparation_civil_engineering;
    private  String Drilling_unit_mob_demob;
    private String Borehole_drilling_construction;
    private String  Well_completion;
    private String Mechanical_circulation_pump;
    private String Heat_exchanger_installation;
    private  String Heat_connection;
    private int  total;
}
