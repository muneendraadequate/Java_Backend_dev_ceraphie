package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostEstimationHeatPump {
    private double Site_preparation_civil_engineering;
    private double Drilling_unit_mob_demob;
    private double Borehole_drilling_construction;
    private double Borehole_completion;
    private double Heat_pump_heat_exchanger_installation;
    private double Heat_connection;
    private double Plate_heat_exchanger;
    private double Total;

}
