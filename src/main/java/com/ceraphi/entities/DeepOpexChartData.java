package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepOpexChartData {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String percentage;
    private String Opex;
    private String npv;
    private String lcoh;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_Information_id")
    private GeneralInformation generalInformation;

}
