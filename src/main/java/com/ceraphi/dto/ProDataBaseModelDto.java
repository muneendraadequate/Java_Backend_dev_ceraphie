package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProDataBaseModelDto {
    private Long id;
    private Integer geothermalGradient;
    private Integer steadyStateTemp;
    private Integer kWt;
    private Integer flowRate;
    private Double pumpingPower;
    private Integer depth;
    private Integer delta;
    private Double pressureLoss;
    private Integer BHT;
    private Double returnValue ;
    private String comment;


}