package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatConnectionCapexDto {
    @NotBlank(message="DistanceToHeatNetwork field is missing")
    private String DistanceToHeatNetwork;
    @NotBlank(message="HeatPipeLineType field is  missing")
    private String HeatPipelineType;
    @NotBlank(message="HeatPipelineCost field is missing")
    private String HeatPipelineCost;
    @NotBlank(message="TotalConnectionCapitalCost field is missing")
    private String TotalConnectionCapitalCost;
    private Long userId;
    private Long key;
}
