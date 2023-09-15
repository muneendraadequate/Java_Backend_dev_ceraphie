package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensitivityAnalysis {
    private Double flowRates;
    private Double thermalOutput;
    private Double withFallOff;
    private Double profit;
    private Double profitWithFallOff;
}
