package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.HeatConnectionCapexDto;
import com.ceraphi.dto.OperationsAndMaintenanceDto;
import com.ceraphi.services.OperationsAndMaintenanceService;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OperationsAndMaintenanceController {
    private OperationsAndMaintenanceService operationsAndMaintenanceService;
    public OperationsAndMaintenanceController(OperationsAndMaintenanceService operationsAndMaintenanceController){
        this.operationsAndMaintenanceService=operationsAndMaintenanceController;
    }
    @PostMapping("/saveTheOperationsAndMaintenance")
    public ResponseEntity<?> saveTheOperationsAndMaintenance(@Valid @RequestBody OperationsAndMaintenanceDto operationsDto, BindingResult result){
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1=new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null,fieldErrors,HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            ApiResponseData<OperationsAndMaintenanceDto> apiResponseData = operationsAndMaintenanceService.saveTheOperationsAndMaintenance(operationsDto);
            if (apiResponseData.getStatus() == HttpStatus.OK.value()) {
                return ResponseEntity.ok(apiResponseData);
            } else if (apiResponseData.getStatus() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseData);
            }
        }
    }


    @PutMapping("/operationsAndMaintenance")
    public ResponseEntity<?> updateCalculation(@Valid @RequestBody OperationsAndMaintenanceDto operationsAndMaintenanceDto , BindingResult result ) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            operationsAndMaintenanceService.updateOperationsAndMaintenance(operationsAndMaintenanceDto.getKey(), operationsAndMaintenanceDto);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("OperationsAndMaintenance updated successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }}

        @GetMapping("/operationsDataBYId/{key}")
                public ResponseEntity<?> geyOperationsDataById(@PathVariable Long key){


            ApiResponseData<OperationsAndMaintenanceDto> operationsDataById = operationsAndMaintenanceService.getOperationsDataById(key);
            return  ResponseEntity.ok(operationsDataById);


        }}

