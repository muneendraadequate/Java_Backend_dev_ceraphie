package com.ceraphi.dto;

import com.ceraphi.entities.GeneralInformation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SurfaceEquipmentDto {
    @NotBlank(message ="InstallationCost field couldn't be empty")
    private String InstallationCost;
    @NotBlank(message = "contingency field couldn't be empty")
    private String contingency;
    @NotBlank(message = "installedHpCapacity field couldn't be empty")
    private String installedHpCapacity;
    @NotBlank( message = "hp_installation_cost field couldn't be empty")
    private String hp_installation_cost;
    @NotBlank( message="surfaceInstallationCost field couldn't be empty")
    private String surfaceInstallationCost;
    @NotBlank( message ="Contingency_Amount field couldn't be empty")
    private String Contingency_Amount;
    private Long  userId;
    private Long key;


}
