package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.HeatConnectionCapexDto;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.services.HeatConnectionCapexService;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class HeatConnectionCapexController {
    private HeatConnectionCapexService heatConnectionCapex;
    private GeneralInformationRepository generalInformationRepo;

    public HeatConnectionCapexController(HeatConnectionCapexService heatConnectionCapex, GeneralInformationRepository generalInformationRepo) {
        this.heatConnectionCapex = heatConnectionCapex;
        this.generalInformationRepo = generalInformationRepo;
    }

    @PostMapping("/saveTheHeatConnectionCapex")
    public ResponseEntity<?> saveHeatConnectionCapex(@Valid @RequestBody HeatConnectionCapexDto heatConnection, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<HeatConnectionCapexDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            ApiResponseData<HeatConnectionCapexDto> apiResponseData = heatConnectionCapex.saveHeatConnectionCapex(heatConnection);
            if (apiResponseData.getStatus() == HttpStatus.OK.value()) {
                return ResponseEntity.ok(apiResponseData);
            } else if (apiResponseData.getStatus() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseData);
            }
        }
    }

    @PutMapping("/heatConnection")
    public ResponseEntity<?> updateCalculation(@Valid @RequestBody HeatConnectionCapexDto heatConnectionCapexDto, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<CostCalculatorDto> apiResponseData = new ApiResponseData<>(null, fieldErrors, HttpStatus.BAD_REQUEST.value(), "Validation error", null);
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            heatConnectionCapex.updateHeatConnection(heatConnectionCapexDto.getKey(), heatConnectionCapexDto);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("heatConnection updated successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }


    }

    @GetMapping("/getHeatConnectionDataById/{key}")
    public ResponseEntity<?> getHeatDataById(@PathVariable Long key) {


        ApiResponseData<HeatConnectionCapexDto> heatDataWithId = heatConnectionCapex.getHeatDataWithId(key);
        return ResponseEntity.ok(heatDataWithId);

    }

}
