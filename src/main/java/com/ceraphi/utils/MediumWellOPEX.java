package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediumWellOPEX {
    private double wellMaintenance;
    private double heatPumpElectricalConsumption;
    private double PumpingPowerConsumption;
}
