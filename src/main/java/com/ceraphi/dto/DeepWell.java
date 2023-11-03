package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepWell {
    private DeepWellOutputsDto deepWellOutputs;
    private DeepCapexDto deepCapex;
    private DeepOpexDto deepOpex;
    private ChartsData chartsData;
}
