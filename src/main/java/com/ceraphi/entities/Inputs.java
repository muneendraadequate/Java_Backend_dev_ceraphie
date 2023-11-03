package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "is_deleted = false")
@Table(name = "Inputs_Table")
public class Inputs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    private String   capacityRequired;
    private String requiredProcessTemp;
    private String  networkLength;
    private String geothermalGradient;
    private String  minOperationalHours;
    private String ambientTemperature;
    private String lifeTimeYears;
    private String sellingPrice;
    private String processDelta;
    private String pumpEfficiency;
    private String deepWellFlowRates;
    private String deepDelta;
    private String connectionPipeThermalConductivity;
    private String connectionPipeThickness;
    private String fuelType;
    private String discountRate_percent;
    private String electricalPrice;
    private Date createdAt;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_information_id")
    private GeneralInformation generalInformation;

}
