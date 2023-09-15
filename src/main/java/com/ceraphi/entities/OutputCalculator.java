package com.ceraphi.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OutputCalculator")
@Where(clause = "is_deleted = false")
public class OutputCalculator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double wellDepth_TargetDepth;
    private Double BottomHoleStaticTemperature;
    private Boolean isWellHeadTemperatureAvailable;
    private Double productionCasting;
    private Double heatExchangerType;
    private Double HE_OuterStringOD;
    private Double HE_InnerStringOD;
    private Double flowRate;
    private Double workingFluid;
    private Double specificHeatDensity;
    private Double WorkingFluidDensity;
    private Double temperatureLoss;
    private Double coolingFluidTemperature;
    private Double longTermThermalFallOff;
    private Double fullLoadHours;
    private Double coolingFluid;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;
    private Long userId;

    public void deleteByGeneralInformationId(Long id) {
    }
}
