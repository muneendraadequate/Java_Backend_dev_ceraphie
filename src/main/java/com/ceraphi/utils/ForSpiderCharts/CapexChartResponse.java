package com.ceraphi.utils.ForSpiderCharts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapexChartResponse {
    private int percentage;
    private BigDecimal capex;
    private BigDecimal NPV;
    private BigDecimal lcoh;
}
