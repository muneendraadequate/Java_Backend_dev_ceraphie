package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.SensitivityAnalysis;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class SensitivityAnalysisController {

    @GetMapping("/sensitivity-analysis")
    public ResponseEntity<?> getStaticSensitivityAnalysisData() {
        List<SensitivityAnalysis> sensitivityAnalyses = new ArrayList<>();

        SensitivityAnalysis sensitivityAnalysis = new SensitivityAnalysis();
        sensitivityAnalysis.setFlowRates(800.90d);
        sensitivityAnalysis.setProfit(2545.51d);
        sensitivityAnalysis.setThermalOutput(4552.5d);
        sensitivityAnalysis.setWithFallOff(4521.24d);
        sensitivityAnalysis.setProfitWithFallOff(345.555d);
        sensitivityAnalyses.add(sensitivityAnalysis);

        SensitivityAnalysis sensitivityAnalysis1 = new SensitivityAnalysis();
        sensitivityAnalysis1.setFlowRates(334.90d);
        sensitivityAnalysis1.setProfit(354.34d);
        sensitivityAnalysis1.setThermalOutput(34.32d);
        sensitivityAnalysis1.setWithFallOff(343.24d);
        sensitivityAnalysis1.setProfitWithFallOff(343.23d);
        sensitivityAnalyses.add(sensitivityAnalysis1);

        SensitivityAnalysis sensitivityAnalysis2 = new SensitivityAnalysis();
        sensitivityAnalysis2.setFlowRates(2300.90d);
        sensitivityAnalysis2.setProfit(1545.51d);
        sensitivityAnalysis2.setThermalOutput(82.5d);
        sensitivityAnalysis2.setWithFallOff(445.24d);
        sensitivityAnalysis2.setProfitWithFallOff(455.555d);
        sensitivityAnalyses.add(sensitivityAnalysis2);

        SensitivityAnalysis sensitivityAnalysis3 = new SensitivityAnalysis();
        sensitivityAnalysis3.setFlowRates(2300.90d);
        sensitivityAnalysis3.setProfit(1545.51d);
        sensitivityAnalysis3.setThermalOutput(82.5d);
        sensitivityAnalysis3.setWithFallOff(445.24d);
        sensitivityAnalysis3.setProfitWithFallOff(455.555d);
        sensitivityAnalyses.add(sensitivityAnalysis2);

        ApiResponseData<?> responseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(sensitivityAnalyses)
                .build();

        return ResponseEntity.ok(responseData);

    }
}
