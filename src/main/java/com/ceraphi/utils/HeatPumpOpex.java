package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeatPumpOpex {
    private String WellMaintenance;
    private String Heat_pump_electrical_consumption;
    private String Pumping_power_consumption;
    private double  totalOpex;
}
