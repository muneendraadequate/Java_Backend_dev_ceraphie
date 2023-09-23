package com.ceraphi.utils.ForSpiderCharts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatChartResponse {
    private int percentage;
    private BigDecimal heatSellingPrice;
    private BigDecimal totalNPV;
}
