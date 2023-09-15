package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.services.GeneralInfoServices;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api")
public class GeneralInformationController {
    private GeneralInfoServices generalInfoServices;

    public GeneralInformationController(GeneralInfoServices generalInfoService) {
        this.generalInfoServices = generalInfoService;

    }


    @PostMapping("/generalInfo")
    public ResponseEntity<?> saveGeneralInformation(@Valid @RequestBody GeneralInformationDto generalInformation, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            ApiResponseData<GeneralInformationDto> apiResponseData = generalInfoServices.saveGeneralInformation(generalInformation);
            if (apiResponseData.getStatus() == HttpStatus.OK.value()) {
                return ResponseEntity.ok(apiResponseData);
            } else if (apiResponseData.getStatus() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseData);
            }
        }
    }

    @PutMapping("/updateGeneralInfo")
    public ResponseEntity<?> updateGeneralInformation(@Valid @RequestBody GeneralInformationDto generalInformation, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            GeneralInformationDto generalInformationDto = generalInfoServices.updateGeneralInformation(generalInformation.getKey(), generalInformation);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .id(generalInformationDto.getKey())
                    .message("GeneralInformation updated successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }


    @GetMapping("/getById/{key}")
    public ResponseEntity<?> getGeneralInfoDataById(@PathVariable Long key) {
        GeneralInformationDto generalInformationById = generalInfoServices.getGeneralInformationById(key);
        return ResponseEntity.ok(generalInformationById);
    }
    @GetMapping("/projectsByUser/{user}")
    public ResponseEntity<?> getByUserId(@PathVariable Long user) {
        List<GeneralInformationDto> byUserId = generalInfoServices.getByUserId(user);
        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(byUserId)
                .build();
        return ResponseEntity.ok(apiResponseData);
    }
    @DeleteMapping("/generalInfoDelete/{id}")
    public ResponseEntity<?> deleteTheGeneralInfo(@PathVariable Long id) {
        generalInfoServices.deleteById(id);

        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .message("deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponseData);

    }
}
