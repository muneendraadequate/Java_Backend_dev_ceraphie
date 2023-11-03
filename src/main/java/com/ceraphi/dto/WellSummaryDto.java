package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class WellSummaryDto {
    private String heatCapacityRequired;
    private String deepCapacityRequired;
    private String heatInitialInvestmentCapex;
    private String deepInitialInvestmentCapex;
    private String heatAnnualO_M_Opex;
    private String deepAnnualO_M_OPex;
    private String heatNpv;
    private String deepNpv;
    private String heatIRR;
    private String deepIRR;
    private String heatP_I;
    private String deepP_I;
    private String heatPaybackPeriod;
    private String deepPaybackPeriod;
    private String heatLcoh;
    private String deepLcoh;
    private boolean heatSelected;
    private boolean deepSelected;
    private int heatWells;
    private int deepWells;
}
