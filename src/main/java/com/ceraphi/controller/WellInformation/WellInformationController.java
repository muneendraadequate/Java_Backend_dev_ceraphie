package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.services.Impl.WellInformationService;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WellInformationController {
    @Autowired
    private WellInformationService wellInformationService;


    @PostMapping("/wellInformation")
    public ResponseEntity<Object> saveWellInformation(@Valid @RequestBody WellInfoDto wellInfo, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1=new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null,fieldErrors,HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            ApiResponseData<WellInfoDto> apiResponseData = wellInformationService.saveWellInformation(wellInfo.getKey(), wellInfo);
            if (apiResponseData.getStatus() == HttpStatus.OK.value()) {
                return ResponseEntity.ok(apiResponseData);
            } else if (apiResponseData.getStatus() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseData);
            }
        }}


    @GetMapping("/wellInformationData/{key}")
    public ResponseEntity<ApiResponseData<List<WellInfoDto>>>getWellDetailsByGeneralInformationId(@PathVariable Long key) {
        ApiResponseData<List<WellInfoDto>> wellInformationByGeneralInformationId = wellInformationService.getWellInformationByGeneralInformationId(key);
        return ResponseEntity.ok(wellInformationByGeneralInformationId);
    }


    @PutMapping("/updateWells")
    public ResponseEntity<?> updateSurfaceEquip(@Valid @RequestBody WellInfoDto wellInfoDto , BindingResult result ) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            wellInformationService.updateWellsData(wellInfoDto.getKey(), wellInfoDto);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }}