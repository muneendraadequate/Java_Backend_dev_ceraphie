package com.ceraphi.entities.MasterDataTables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "heat_load_fuels")
public class HeatLoadFuels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fuelType;
    private String efficiency;
    private String carbon;
    private String nox;
    private String noxN;
    private String ghg;
public HeatLoadFuels(HeatLoadFuels heatLoadFuels){
    this.fuelType = heatLoadFuels.getFuelType();
    this.efficiency = heatLoadFuels.getEfficiency();
    this.carbon = heatLoadFuels.getCarbon();
    this.nox = heatLoadFuels.getNox();
    this.noxN = heatLoadFuels.getNoxN();
    this.ghg = heatLoadFuels.getGhg();
}
}
