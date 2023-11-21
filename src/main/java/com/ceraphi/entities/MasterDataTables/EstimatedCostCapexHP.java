package com.ceraphi.entities.MasterDataTables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estimated_cost_capex_hp")
public class EstimatedCostCapexHP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operation;
    private double cost;
    private String perWell;

    public EstimatedCostCapexHP(EstimatedCostCapexHP estimatedCostCapexHP) {
        this.operation = estimatedCostCapexHP.getOperation();
        this.cost = estimatedCostCapexHP.getCost();
        this.perWell = estimatedCostCapexHP.getPerWell();
    }
}
