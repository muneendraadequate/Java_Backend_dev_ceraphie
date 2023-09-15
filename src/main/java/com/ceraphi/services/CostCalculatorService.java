package com.ceraphi.services;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.ResponseEntity;

public interface CostCalculatorService {
    ApiResponseData<CostCalculatorDto> saveCostCalculation(CostCalculatorDto costCalculatorDto);
    ApiResponseData<CostCalculatorDto> updateCostCalculation(long id, CostCalculatorDto costCalculatorDto );
    ApiResponseData<CostCalculatorDto> getCostDataById(Long id);
    void deleteByGeneralInformationId(Long id);
}
