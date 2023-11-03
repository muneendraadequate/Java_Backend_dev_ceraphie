package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepOpexDto {
    private String mechanicalPumpPowerConsumption;
    private String boostPumpPowerConsumption;
    private String wellMaintenance;
    private double  total;
}
