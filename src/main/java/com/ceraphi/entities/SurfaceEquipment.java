package com.ceraphi.entities;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "surface_equipment")
@Where(clause = "is_deleted = false")
public class SurfaceEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String InstallationCost;
    private String contingency;
    private String installedHpCapacity;
    private String hp_installation_cost;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    private String surfaceInstallationCost;
    private String Contingency_Amount;
    private Long userId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;


}
