package com.ceraphi.utils;

import com.ceraphi.dto.DeepWellOutPut;
import com.ceraphi.dto.OutPutCalculationHeatPump;
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
    //Lcoh
//    private List<LCOHYearResponse> lcohYearResponseDeepWell = new ArrayList<LCOHYearResponse>();
//    private List<LCOHYearResponse> lcohYearResponseHeatPumpWell = new ArrayList<LCOHYearResponse>();

}
