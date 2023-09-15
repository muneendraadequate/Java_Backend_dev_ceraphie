package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "heat_connection_capex")
@Where(clause = "is_deleted = false")
public class HeatConnectionCapex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String DistanceToHeatNetwork;
    private String HeatPipelineType;
    private String HeatPipelineCost;
    private String TotalConnectionCapitalCost;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    private Long userId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;

}
