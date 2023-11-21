package com.ceraphi.entities.MasterDataTables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Gel_Data_1500M_Well")
public class GelDataWell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  tempRequired;

    private String flowRate;

    private String COP;

    private String wellOutletTemp;

    private String capacity;
    public  GelDataWell(GelDataWell gelDataWell){
        this.tempRequired = gelDataWell.getTempRequired();
        this.flowRate = gelDataWell.getFlowRate();
        this.COP = gelDataWell.getCOP();
        this.wellOutletTemp = gelDataWell.getWellOutletTemp();
        this.capacity = gelDataWell.getCapacity();
    }
}
