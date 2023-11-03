package com.ceraphi.dto.MasterDataTablesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimatedCostCapexDeepDto {
    private Long id;
    private String operation;
    private double cost;
    private String perWell;
}
