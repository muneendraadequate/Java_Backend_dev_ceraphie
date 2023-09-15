package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationsAndMaintenanceDto {
    @NotBlank(message = "totalFixed_O_M field data is missing")
    private String totalFixed_O_M;
    @NotBlank(message="variableOMPerUnit field data is missing")
    private String variableOMPerUnit;
    @NotBlank(message="total_O_M field data is missing")
    private String total_O_M;
    @NotBlank(message="total_fixed_OM_per_unit field data is missing")
    private String total_fixed_OM_per_unit;
    @NotBlank(message="total_variable_O_M field data is missing")
    private String total_variable_O_M;
    @NotBlank(message="O_M_cost_per_unit field data is missing")
    private String O_M_cost_per_unit;
    private Long key;
    private Long userId;
}
