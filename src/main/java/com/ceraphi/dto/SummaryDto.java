package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SummaryDto {
    private double mediumWellCapex;
    private double mediumWellOpex;
    private double deepWellCapex;
    private double deepWellOpex;
    private int year;
}
