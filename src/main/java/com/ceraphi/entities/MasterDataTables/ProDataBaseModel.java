package com.ceraphi.entities.MasterDataTables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="proDataBase")
public class ProDataBaseModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "geothermal_gradient")
    private Integer geothermalGradient;
    @Column(name="steady_state_temp")
    private Integer steadyStateTemp;
    @Column(name="k_wt")
    private Integer kWt;
    @Column(name="flow_rate")
    private Integer flowRate;
    @Column(name="pumping_power")
    private Double pumpingPower;
    @Column(name = "depth")
    private Integer depth;
    @Column(name="delta")
    private Integer delta;
    @Column(name="pressure_loss")
    private Double pressureLoss;
    @Column(name="bht")
    private Integer BHT;
    @Column(name="return_value")
    private Double returnValue ;

}
