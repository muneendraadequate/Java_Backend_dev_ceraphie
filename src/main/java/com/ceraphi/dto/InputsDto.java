package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputsDto {
    private String capacityRequired;
    private String requiredProcessTemp;
    private String networkLength;
    private String geothermalGradient;
    private String minOperationalHours;
    private String ambientTemperature;
    private String lifeTimeYears;
    private String sellingPrice;
    private String processDelta;
    private String pumpEfficiency;
    private String deepWellFlowRates;
    private String deepDelta;
    private String connectionPipeThermalConductivity;
    private String connectionPipeThickness;
    private String fuelType;
    private String discountRate_percent;
    private String electricalPrice;
//    private Long projectId;
}
