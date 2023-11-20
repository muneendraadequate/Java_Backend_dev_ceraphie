package com.ceraphi.entities.MasterDataTables;

//import com.ceraphi.dto.MasterDataTablesDto.ProDataBaseModelListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="proDataBase")
public class ProDataBaseModel implements Serializable {
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
//    @OneToMany(mappedBy = "proDataBaseModel", cascade = CascadeType.ALL)
//    private List<ChangeLog> changeLogs;
    public ProDataBaseModel(ProDataBaseModel source) {
        this.geothermalGradient = source.getGeothermalGradient();
        this.steadyStateTemp = source.getSteadyStateTemp();
        this.kWt = source.getKWt();
        this.flowRate = source.getFlowRate();
        this.pumpingPower = source.getPumpingPower();
        this.depth = source.getDepth();
        this.delta = source.getDelta();
        this.pressureLoss = source.getPressureLoss();
        this.BHT = source.getBHT();
        this.returnValue = source.getReturnValue();
        // Copy other fields as needed
    }
}
