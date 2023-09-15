package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OutputCalculatorDto {

    private Double wellDepth_TargetDepth;
    private Double BottomHoleStaticTemperature;
    private Boolean isWellHeadTemperatureAvailable;
    private Double productionCasting;
    private Double heatExchangerType;
    private Double HE_OuterStringOD;
    private Double HE_InnerStringOD;
    private Double flowRate;
    private Double workingFluid;
    private Double specificHeatDensity;
    private Double WorkingFluidDensity;
    private Double temperatureLoss;
    private Double coolingFluidTemperature;
    private Double longTermThermalFallOff;
    private Double fullLoadHours;
    private Double coolingFluid;
    private Long key;
    private Long userId;
}
