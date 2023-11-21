package com.ceraphi.entities.MasterDataTables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estimated_cost_opex_deep")
public class EstimatedCostOpexDeep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operation;
    private double cost;
    private String perWell;

    public EstimatedCostOpexDeep(EstimatedCostOpexDeep currentModel) {
            this.operation = currentModel.getOperation();
            this.cost = currentModel.getCost();
            this.perWell = currentModel.getPerWell();
        }
    }
