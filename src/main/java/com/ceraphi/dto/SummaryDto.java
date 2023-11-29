package com.ceraphi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SummaryDto {
    private Double discountRate;
    private Double sellingPrice;
    private Double costInflation;
    private Double electricalPriceInflation;
    private double mediumWellCapex;
    private double mediumWellOpex;
    private double deepWellCapex;
    private double deepWellOpex;
    private int year;
}
