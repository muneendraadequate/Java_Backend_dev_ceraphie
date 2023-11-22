package com.ceraphi.dto.MasterDataTablesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatLoadFuelsDto {
    private Long id;
    private String fuelType;
    private String efficiency;
    private String carbon;
    private String nox;
    private String noxN;
    private String ghg;
    private String comment;

}
