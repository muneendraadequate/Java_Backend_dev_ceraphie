package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HeatPumpAssistedWell")
@Entity
public class HeatPumpAssistedWell implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Double capacityReq;
    private Double processRequiredTemp;
    private Double networkLength;
    private Double minOperationalHours;
    private Double thermalGradient;
    private Double ambientTemperature;
    private Double lifeTime;
    private Double sellingPricePer_kWh;
}
