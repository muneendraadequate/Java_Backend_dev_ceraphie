package com.ceraphi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CostCalculatorDto {
    @NotBlank(message = " HeatSaleData field  data is missing")
    private String heatSalePrice;
    @NotBlank(message = "OperationalLifeTime field data is missing")
    private String operationalLifetime;
    @NotBlank(message="LoanRepaymentPeriod field data is missing")
    private String loanRepaymentPeriod;
    @NotBlank(message="DiscountRate field data is missing")
    private String discountRate;
    @NotBlank(message = "InflationRate field data is missing")
    private String inflationRate;
    @NotBlank(message="PotentialRevenue field data is missing")
    private String potentialRevenue;
    @NotBlank (message="OperatingIncome field data is missing")
    private String operatingIncome;
    private Long userId;
    private Long key;
}
