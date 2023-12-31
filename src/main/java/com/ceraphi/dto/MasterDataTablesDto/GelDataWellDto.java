package com.ceraphi.dto.MasterDataTablesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GelDataWellDto {
    private Long id;

    private String  tempRequired;

    private String flowRate;

    private String COP;

    private String wellOutletTemp;

    private String capacity;
    private String comment;

}
