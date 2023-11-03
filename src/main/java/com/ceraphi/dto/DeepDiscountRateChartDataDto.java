package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepDiscountRateChartDataDto {
    private String percentage ;
    private String discountRate;
    private String npv;
    private String lcoh;
}
