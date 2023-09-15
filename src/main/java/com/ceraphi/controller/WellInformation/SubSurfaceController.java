package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.SubSurfaceDto;
import com.ceraphi.services.SubSurfaceService;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SubSurfaceController {
    private final SubSurfaceService subSurfaceService;

    public SubSurfaceController(SubSurfaceService subSurfaceService) {
        this.subSurfaceService = subSurfaceService;
    }

    @PostMapping("/saveSubSurfaceDetails")
    private ResponseEntity<?> subSurfaceDetails(@Valid @RequestBody SubSurfaceDto subSurface, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            ApiResponseData<SubSurfaceDto> apiResponseData = subSurfaceService.saveSubSurfaceData(subSurface);
            if (apiResponseData.getStatus() == HttpStatus.OK.value()) {
                return ResponseEntity.ok(apiResponseData);
            } else if (apiResponseData.getStatus() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseData);
            }
        }
    }

    @PutMapping("/updateSubSurface")
    public ResponseEntity<?> updateSubSurface(@Valid @RequestBody SubSurfaceDto subSurfaceDto, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            subSurfaceService.updateSubSurface(subSurfaceDto.getKey(), subSurfaceDto);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("SubSurfaceData updated successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }

    @GetMapping("/getSubSurfaceDataById/{key}")
    public ResponseEntity<?> getSubSurfaceData(@PathVariable Long key) {
        ApiResponseData<SubSurfaceDto> subSurfaceData = subSurfaceService.getSubSurfaceData(key);
        return ResponseEntity.ok(subSurfaceData);
    }

}




