package com.ceraphi.services;

import com.ceraphi.dto.OutputCalculatorDto;
import com.ceraphi.utils.ApiResponseData;
import io.swagger.annotations.Api;

public interface OutputCalculatorService {
   ApiResponseData<?> saveOutPutCalculator(OutputCalculatorDto outputCalculatorDto);
    void deleteByGeneralInformationId(Long id);
}
