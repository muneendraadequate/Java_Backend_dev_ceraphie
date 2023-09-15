package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "cost_calculator")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class CostCalculator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String heatSalePrice;
    private String operationalLifetime;
    private String loanRepaymentPeriod;
    private String discountRate;
    private String inflationRate;
    private String potentialRevenue;
    private String operatingIncome;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;
}
