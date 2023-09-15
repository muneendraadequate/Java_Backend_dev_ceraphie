package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelColumns {
    private double geothermalGradient;
    private double steadyStateTemp;
    private double kWt;
    private double flowRate;
    private double pumpingPower;
    private double Depth;
    private double delta;
    private double pressureLoss;
    private double BHT;
    private double Return;
}
