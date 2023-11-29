package com.ceraphi.controller.DashBoardController;

import com.ceraphi.dto.DashBoardResponseDto;
import com.ceraphi.services.Impl.DashBoardService;
import com.ceraphi.utils.DashBoard.GeographicalAnalysis;
import com.ceraphi.utils.DashBoard.TotalValues;
import com.ceraphi.utils.DashBoard.WellByType;
import com.ceraphi.utils.DashBoard.WellsByRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DashBoard {
    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("/getDashBoard/{userId}")
    public ResponseEntity<DashBoardResponseDto> getCombinedData(@PathVariable("userId") Long userId) {
        List<GeographicalAnalysis> WellsByCountry = dashBoardService.getTheWellsByCountry(userId);
        List<WellsByRegion> wellsByRegion = dashBoardService.getProjectTypesByCountryAndUserId(userId);
        List<WellByType> wellType = dashBoardService.getWellCountsByUserId(userId);
        int totalProjectsForUser = dashBoardService.getTotalProjectsForUser(userId);
        int totalMG = dashBoardService.calculateTotalCapacity();
        int totalCapex = dashBoardService.calculateTotalInvestment();
        Integer heatWells = dashBoardService.countHeatWellsWithHeatSelected();
        Integer deepWells = dashBoardService.countDeepWellsWithDeepSelected();
        TotalValues totalValues=new TotalValues(totalProjectsForUser,totalMG,totalCapex,heatWells,deepWells);



        DashBoardResponseDto combinedData = new DashBoardResponseDto(WellsByCountry, wellsByRegion, wellType, totalValues);

        return ResponseEntity.ok(combinedData);
    }}