package com.ceraphi.utils;

import com.ceraphi.dto.DeepWellOutPut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoubleResponseDeep {

        private CostEstimationDeepWell costEstimationDeepWell;
        private DeepWellOutPut DeepWellOutPutCalculation;
        private DeepWellOpex deepWellOpex;
    }

