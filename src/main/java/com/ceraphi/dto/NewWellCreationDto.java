package com.ceraphi.dto;

import com.ceraphi.entities.WellInformation;
import com.ceraphi.services.WellService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewWellCreationDto {
    private GeneralInformationDto generalInformationDto;
    private Integer generalInformationId;
    private InputsDto inputsDto;
    private WellSummaryDto wellSummaryDto;
}
