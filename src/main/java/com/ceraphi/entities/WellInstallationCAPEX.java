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
@Where(clause = "is_deleted = false")
public class WellInstallationCAPEX {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private int workOver_rig_day_rate;
    private int Duration;
    private Double non_Productive_time;
    private Double centralizer_cost;
    private Double dual_completion_tubing_hanger;
    private Double tubing_hanger_spool;
    private Double landing_nipple_outer_string;
    private Double landing_nipple_inner_string;
    private Double contingency;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;
    private Long userId;
}
