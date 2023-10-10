package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeepWellOpex {
    private String mechanicalPumpPowerConsumption;
    private String boostPumpPowerConsumption;
    private String wellMaintenance;
    private double  total;
}
