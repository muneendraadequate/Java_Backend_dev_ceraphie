package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeatPumpOpex {
    private double WellMaintenance;
    private double Heat_pump_electrical_consumption;
    private double Pumping_power_consumption;
    private double totalOpex;
}
