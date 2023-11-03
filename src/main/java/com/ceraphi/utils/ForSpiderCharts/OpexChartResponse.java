package com.ceraphi.utils.ForSpiderCharts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpexChartResponse {
    private int percentage;
    private BigDecimal opex;
    private BigDecimal NPV;
    private BigDecimal lcoh;
}
