package com.ceraphi.dto.MasterDataTablesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeSetDto {
    private Long id;
    private String timestamp;
    private String comment;
}