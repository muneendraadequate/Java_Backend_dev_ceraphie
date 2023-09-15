package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeepWellOpex {
    private double mechanicalPumpPowerConsumption;
    private double boostPumpPowerConsumption;
    private double wellMaintenance;
    private double total;
}
