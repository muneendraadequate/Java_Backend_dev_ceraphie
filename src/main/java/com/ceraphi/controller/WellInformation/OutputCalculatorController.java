package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.OutputCalculatorDto;
import com.ceraphi.entities.OutputCalculator;
import com.ceraphi.services.OutputCalculatorService;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OutputCalculatorController {

    private OutputCalculatorService outputCalculator;
    public OutputCalculatorController(OutputCalculatorService outputCalculator){
        this.outputCalculator=outputCalculator;
    }
    @PostMapping("/output-calculator")
    public ResponseEntity<ApiResponseData<?>> saveOutPutCalculator(@RequestBody OutputCalculatorDto outputCalculatorDto) {
        ApiResponseData<?> apiResponseData = outputCalculator.saveOutPutCalculator(outputCalculatorDto);

        if (apiResponseData.getStatus() == HttpStatus.OK.value() || apiResponseData.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            return ResponseEntity.ok(apiResponseData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    }