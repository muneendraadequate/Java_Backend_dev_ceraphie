package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.SubSurfaceDto;
import com.ceraphi.dto.SurfaceEquipmentDto;
import com.ceraphi.entities.SurfaceEquipment;
import com.ceraphi.services.SurfaceEquipmentService;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SurfaceEquipmentController {
    private final SurfaceEquipmentService surfaceEquipmentService;

    public SurfaceEquipmentController(SurfaceEquipmentService surfaceEquipmentService) {
        this.surfaceEquipmentService = surfaceEquipmentService;
    }

    @PostMapping("/saveEquipment")
    private ResponseEntity<?> subSurfaceDetails(@Valid @RequestBody SurfaceEquipmentDto surfaceEquipment, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            ApiResponseData<SurfaceEquipmentDto> apiResponseData = surfaceEquipmentService.saveTheSurfaceEquip(surfaceEquipment);
            if (apiResponseData.getStatus() == HttpStatus.OK.value()) {
                return ResponseEntity.ok(apiResponseData);
            } else if (apiResponseData.getStatus() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseData);
            }
        }
    }

    @PutMapping("/updateSurfaceEquipment")
    public ResponseEntity<?> updateSurfaceEquip(@Valid @RequestBody SurfaceEquipmentDto surfaceEquipmentDto, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            surfaceEquipmentService.updateSurfaceEquipment(surfaceEquipmentDto.getKey(), surfaceEquipmentDto);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("surfaceEquipmentData updated successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }

    @GetMapping("/getSurfaceEquipData/{key}")
    public ResponseEntity<?> getSurfaceEquipData(@PathVariable Long key) {

        ApiResponseData<SurfaceEquipmentDto> dataById = surfaceEquipmentService.getDataById(key);
        return ResponseEntity.ok(dataById);


    }
}


