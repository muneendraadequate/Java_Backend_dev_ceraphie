package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostEstimationHeatPump {
    private String Site_preparation_civil_engineering;
    private String Drilling_unit_mob_demob;
    private String Borehole_drilling_construction;
    private String Borehole_completion;
    private String Heat_pump_heat_exchanger_installation;
    private String Heat_connection;
    private String Plate_heat_exchanger;
    private double  Total;

}
