package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SummaryData")
@Where(clause = "is_deleted = false")
public class WellSummaryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String heatCapacityRequired;
    private String deepCapacityRequired;
    private String heatInitialInvestment;
    private String deepInitialInvestment;
    private String heatAnnualO_M;
    private String deepAnnualO_M;
    private String heatNpv;
    private String deepNpv;
    private String heatIRR;
    private String deepIRR;
    private String heatP_I;
    private String deepP_I;
    private String heatPaybackPeriod;
    private String deepPaybackPeriod;
    private String heatLcoh;
    private String deepLcoh;
    private boolean heatSelected;
    private boolean deepSelected;
    private int heatWells;
    private int deepWells;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;

}
