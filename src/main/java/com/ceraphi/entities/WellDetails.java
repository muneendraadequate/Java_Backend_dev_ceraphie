package com.ceraphi.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "well_detail")
public class WellDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wellName;
    private String wellType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_information_id")
    private WellInformation wellInformation;


    public WellInformation getWellInformation() {
        return wellInformation;
    }

    public void setWellInformation(WellInformation wellInformation) {
        this.wellInformation = wellInformation;
    }

    public void updateWellDetails(WellDetails well) {
        this.wellName = well.getWellName();
        this.wellType = well.getWellType();
        this.wellInformation = well.getWellInformation();

    }
}
