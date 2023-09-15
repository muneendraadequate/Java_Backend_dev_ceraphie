package com.ceraphi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SubSurfaceDto {
    @NotBlank(message = "workOverRigMobilisation field data is missing")
    private String workOverRigMobilisation;
    @NotBlank(message="rigUpEstimateWithCranes_etc field data is missing")
    private String rigUpEstimateWithCranes_etc;
    @NotBlank(message="workOverRigDeMobilisation field data is missing")
    private String workOverRigDeMobilisation;
    @NotBlank(message="rigDownEstimate field data is missing")
    private String rigDownEstimate;
    @NotBlank(message = "SubsurfaceInstallationCost field data is missing")
    private String SubsurfaceInstallationCost;
    private Long userId;
    private Long key;
}
