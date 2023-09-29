package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.CostCalculatorDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.InputStream;
import com.ceraphi.services.CostCalculatorService;
import com.ceraphi.utils.ApiResponseData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CostCalculatorController {
    private CostCalculatorService costCalculatorService;

    public CostCalculatorController(CostCalculatorService costCalculatorService) {
        this.costCalculatorService = costCalculatorService;
    }

    @PostMapping("/saveTheCostCalculation")
    public ResponseEntity<ApiResponseData<?>> saveTheCostCalculation(@Valid @RequestBody CostCalculatorDto costCalculatorDto, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder().errors(fieldErrors).status(HttpStatus.BAD_REQUEST.value()).message("Validation error").build();
//            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            ApiResponseData<CostCalculatorDto> apiResponseData = costCalculatorService.saveCostCalculation(costCalculatorDto);
            if (apiResponseData.getStatus() == HttpStatus.OK.value()) {
                return ResponseEntity.ok(apiResponseData);
            } else if (apiResponseData.getStatus() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseData);
            }
        }
    }


    @PutMapping("/updateCalculation")
    public ResponseEntity<?> updateCalculation(@Valid @RequestBody CostCalculatorDto costCalculatorDto, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            costCalculatorService.updateCostCalculation(costCalculatorDto.getKey(), costCalculatorDto);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("CostCalculation updated successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }}

        @GetMapping("/getCostDataById/{key}")
        public ResponseEntity<?> getCostDataById(@PathVariable Long key){

            ApiResponseData<CostCalculatorDto> costDataById = costCalculatorService.getCostDataById(key);
            return ResponseEntity.ok(costDataById);
        }
    }