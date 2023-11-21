package com.ceraphi.entities.MasterDataTables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.GenerationType;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estimated_cost_capex_deep")
public class EstimatedCostCapexDeep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operation;
    private double cost;
    private String perWell;

    public EstimatedCostCapexDeep(EstimatedCostCapexDeep estimatedCostCapexDeep) {
        this.operation = estimatedCostCapexDeep.getOperation();
        this.cost = estimatedCostCapexDeep.getCost();
        this.perWell = estimatedCostCapexDeep.getPerWell();
    }
}
