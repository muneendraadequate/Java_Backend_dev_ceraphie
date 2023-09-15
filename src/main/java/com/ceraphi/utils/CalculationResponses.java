package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationResponses {
    private CostEstimationHeatPump costEstimationHeatPump;
    private OutPutCalculationHeatPump outPutCalculationHeatPump;
    private HeatPumpOpex heatPumpOpex;
    private EmissionData emissionData;

    //deep well
    private CostEstimationDeepWell costEstimationDeepWell;
    private DeepWellOutPut DeepWellOutPutCalculation;
    private DeepWellOpex deepWellOpex;
}
