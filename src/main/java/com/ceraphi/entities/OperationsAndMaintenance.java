package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations_maintenance")
@Where(clause = "is_deleted = false")
public class OperationsAndMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String totalFixed_O_M;
    private String variableOMPerUnit;
    private String total_O_M;
    private String total_fixed_OM_per_unit;
    private String total_variable_O_M;
    private String O_M_cost_per_unit;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;
    private Long userId;
}
