package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostEstimationDeepWell {
    private double Site_preparation_civil_engineering;
    private  double Drilling_unit_mob_demob;
    private double Borehole_drilling_construction;
   private double  Well_completion;
    private double Mechanical_circulation_pump;
    private double Heat_exchanger_installation;
    private  double Heat_connection;
    private double total;


}
