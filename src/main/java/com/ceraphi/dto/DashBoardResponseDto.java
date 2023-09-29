package com.ceraphi.dto;

import com.ceraphi.utils.DashBoard.GeographicalAnalysis;
import com.ceraphi.utils.DashBoard.TotalValues;
import com.ceraphi.utils.DashBoard.WellByType;
import com.ceraphi.utils.DashBoard.WellsByRegion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardResponseDto {
    private List<GeographicalAnalysis> WellsByCountry;
    private List<WellsByRegion> wellsByRegion;
    private List<WellByType> wellType;
    private TotalValues totalValues;
}
