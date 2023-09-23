package com.ceraphi.utils.ForSpiderCharts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WellChartResponse {
   public  List<DiscountRateResponse> discountRateResponse;
    public List<OpexChartResponse > opexChartResponse;
    public List<CapexChartResponse> capexChartResponses;
    public List<HeatChartResponse> heatChartResponses;
}
