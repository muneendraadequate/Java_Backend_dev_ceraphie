package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "sub_surface")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class SubSurface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String workOverRigMobilisation;
    private String rigUpEstimateWithCranes_etc;
    private String workOverRigDeMobilisation;
    private String rigDownEstimate;
    private String SubsurfaceInstallationCost;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    private Long userId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;
}
