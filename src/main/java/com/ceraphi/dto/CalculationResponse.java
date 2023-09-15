package com.ceraphi.dto;

import com.ceraphi.utils.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResponse {
   private CostEstimationHeatPump costEstimationHeatPump;
   private OutPutCalculationHeatPump outputCalculation;
   private CostEstimationDeepWell costEstimationDeepWell;
   private DeepWellOutPut deepWellOutPut;
   private DeepWellOpex deepWellOpex;
}
