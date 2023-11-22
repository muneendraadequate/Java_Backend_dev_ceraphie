package com.ceraphi.dto.MasterDataTablesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimatedCostCapexDeepDto {
    private Long id;
    private String operation;
    private double cost;
    private String perWell;
    private String comment;


}
