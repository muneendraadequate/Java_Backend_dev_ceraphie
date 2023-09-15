package com.ceraphi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WellInstallationCAPEXDto {
    private Double workOver_rig_day_rate;
    private Double Duration;
    private Double non_Productive_time;
    private Double centralizer_cost;
    private Double dual_completion_tubing_hanger;
    private Double tubing_hanger_spool;
    private Double landing_nipple_outer_string;
    private Double landing_nipple_inner_string;
    private Double contingency;
    private Long key;
    private Long userId;
}
